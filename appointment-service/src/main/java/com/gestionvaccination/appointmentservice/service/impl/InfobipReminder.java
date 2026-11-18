package com.gestionvaccination.appointmentservice.service.impl;

import com.gestionvaccination.appointmentservice.service.AudioReminderService;
// Twilio désactivé (solde épuisé)
// import com.twilio.rest.api.v2010.account.Call;
// import com.twilio.type.PhoneNumber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class InfobipReminder implements AudioReminderService {

    // Twilio désactivé
    // @Value("${twilio.phone.number}")
    // private String twilioPhoneNumber;

    @Value("${infobip.api.key}")
    private String infobipApiKey;

    @Value("${infobip.base.url}")
    private String infobipBaseUrl;

    @Value("${infobip.whatsapp.from}")
    private String infobipFromNumber;

    @Value("${audio.url.base}")
    private String audioBaseUrl;

    // ★ NOUVEAUTÉ : template avec IMAGE + emojis (pour mamans analphabètes)
    // Appeler POST /api/v1/appointments/whatsapp/create-template UNE SEULE FOIS pour le soumettre à WhatsApp
    // Après approbation (24-48h), il s'active automatiquement
    private static final String TEMPLATE_IMAGE_FR = "vaccination_maman_fr";

    // Template de secours anglais (déjà approuvé, fonctionne maintenant)
    private static final String TEMPLATE_FALLBACK_EN = "test_whatsapp_template_en";

    // ★ NOUVEAUTÉ : URL image bébé/vaccination — visible même sans savoir lire
    private static final String IMAGE_VACCINATION_URL =
        "https://cdn.pixabay.com/photo/2016/11/18/14/05/baby-1835730_1280.jpg";

    @Override
    public void sendAudioReminder(String phoneNumber, String langue) {
        // Fallback sans noms — délègue à la méthode professionnelle avec valeurs par défaut
        sendAudioReminder(phoneNumber, langue, "cher parent", "votre enfant", "vaccin");
    }

    @Override
    public void sendAudioReminder(String phoneNumber, String langue,
                                   String prenomNomParent, String prenomNomEnfant, String vaccin) {
        try {
            String toNumber = phoneNumber.startsWith("221") ? phoneNumber : "221" + phoneNumber;
            log.info("Envoi rappel WhatsApp professionnel vers +{}", toNumber);

            // ★ NOUVEAUTÉ : essayer d'abord le template avec image en français (mamans analphabètes)
            // Ce template montre une image de bébé + emojis — compréhensible sans savoir lire
            boolean sent = sendImageTemplate(toNumber, TEMPLATE_IMAGE_FR, "fr",
                new String[]{prenomNomParent, prenomNomEnfant, vaccin != null ? vaccin : "vaccin"},
                IMAGE_VACCINATION_URL);

            // ★ NOUVEAUTÉ : si le template image n'est pas encore approuvé → fallback anglais (fonctionne)
            if (!sent) {
                log.warn("Template image {} non disponible, utilisation du template de secours anglais", TEMPLATE_IMAGE_FR);
                sendTemplate(toNumber, TEMPLATE_FALLBACK_EN, "en", new String[]{prenomNomEnfant});
            }

            // Appel vocal TTS — désactivé : le compte trial Infobip n'a pas de numéro vocal activé
            // Pour activer : demander un numéro Voice dans le portail Infobip > Numbers > Voice
            // sendVoiceCall(toNumber);

        } catch (Exception e) {
            log.error("Erreur lors de l'envoi du rappel WhatsApp", e);
        }
    }

    private boolean sendTemplate(String toNumber, String templateName, String language, String[] placeholders) {
        try {
            StringBuilder placeholdersJson = new StringBuilder("[");
            for (int i = 0; i < placeholders.length; i++) {
                placeholdersJson.append("\"").append(placeholders[i].replace("\"", "'")).append("\"");
                if (i < placeholders.length - 1) placeholdersJson.append(",");
            }
            placeholdersJson.append("]");

            String requestBody = String.format(
                "{\"messages\":[{\"from\":\"%s\",\"to\":\"%s\",\"content\":{" +
                "\"templateName\":\"%s\"," +
                "\"templateData\":{\"body\":{\"placeholders\":%s}}," +
                "\"language\":\"%s\"}}]}",
                infobipFromNumber, toNumber, templateName, placeholdersJson, language
            );

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://" + infobipBaseUrl + "/whatsapp/1/message/template"))
                    .header("Authorization", "App " + infobipApiKey)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                // Infobip retourne 200 même si le template est rejeté — vérifier le body
                if (responseBody.contains("REJECTED") || responseBody.contains("rejected") ||
                    responseBody.contains("TEMPLATE_NOT_FOUND") || responseBody.contains("UNDELIVERABLE")) {
                    log.warn("Template {} rejeté par WhatsApp (HTTP 200 mais REJECTED): {}", templateName, responseBody);
                    return false;
                }
                log.info("✅ WhatsApp [{}] envoyé à +{} | Réponse: {}", templateName, toNumber, responseBody);
                return true;
            } else if (response.statusCode() == 400) {
                log.warn("Template {} non disponible (400): {}", templateName, responseBody);
                return false;
            } else {
                log.error("Erreur WhatsApp Infobip [{}]: {} - {}", templateName, response.statusCode(), responseBody);
                return false;
            }
        } catch (Exception e) {
            log.error("Erreur sendTemplate [{}]", templateName, e);
            return false;
        }
    }

    // ★ NOUVEAUTÉ : crée le template avec IMAGE (pour mamans analphabètes)
    // Appeler UNE SEULE FOIS via POST /api/v1/appointments/whatsapp/create-template
    // Après approbation WhatsApp (24-48h), la maman reçoit une image de bébé + emojis en français
    @Override
    public String createVaccinationTemplate() {
        try {
            String templateBody =
                "{\"name\":\"vaccination_maman_fr\"," +
                "\"language\":\"fr\"," +
                "\"category\":\"UTILITY\"," +
                "\"structure\":{" +
                // IMAGE en tête — visible même sans savoir lire
                "\"header\":{\"type\":\"IMAGE\"," +
                "\"example\":[\"" + IMAGE_VACCINATION_URL + "\"]}," +
                // Corps avec emojis = communication visuelle pour analphabètes
                "\"body\":{\"text\":" +
                "\"Bonjour {{1}} \\uD83D\\uDC4B\\n\\n" +
                "\\uD83D\\uDC89 Votre enfant \\uD83D\\uDC76 *{{2}}* doit \\u00eatre vaccin\\u00e9 !\\n\\n" +
                "\\uD83D\\uDC8A Vaccin : *{{3}}*\\n\\n" +
                "\\uD83C\\uDFE5 Rendez-vous au centre de sant\\u00e9\\n" +
                "\\uD83D\\uDCC5 Ne manquez pas ce rendez-vous !\\n\\n" +
                "\\u2705 \\uD83D\\uDC76 La vaccination prot\\u00e8ge votre enfant !\"," +
                "\"examples\":[\"Fatou Diallo\",\"Moussa Diallo\",\"BCG\"]}," +
                "\"footer\":{\"text\":\"\\uD83D\\uDC8A Vaccination S\\u00e9n\\u00e9gal \\uD83C\\uDDF8\\uD83C\\uDDF3\"}," +
                "\"type\":\"MEDIA\"}}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://" + infobipBaseUrl + "/whatsapp/2/senders/" + infobipFromNumber + "/templates"))
                    .header("Authorization", "App " + infobipApiKey)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(templateBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Création template image vaccination_maman_fr: {} - {}", response.statusCode(), response.body());
            return response.body();
        } catch (Exception e) {
            log.error("Erreur création template image", e);
            return "Erreur: " + e.getMessage();
        }
    }

    // ★ NOUVEAUTÉ : envoi template WhatsApp avec IMAGE en tête
    // L'image s'affiche directement dans WhatsApp — compréhensible pour les mamans analphabètes
    private boolean sendImageTemplate(String toNumber, String templateName, String language,
                                       String[] placeholders, String imageUrl) {
        try {
            StringBuilder placeholdersJson = new StringBuilder("[");
            for (int i = 0; i < placeholders.length; i++) {
                placeholdersJson.append("\"").append(placeholders[i].replace("\"", "'")).append("\"");
                if (i < placeholders.length - 1) placeholdersJson.append(",");
            }
            placeholdersJson.append("]");

            String requestBody = String.format(
                "{\"messages\":[{\"from\":\"%s\",\"to\":\"%s\",\"content\":{" +
                "\"templateName\":\"%s\"," +
                "\"templateData\":{" +
                "\"header\":{\"type\":\"IMAGE\",\"mediaUrl\":\"%s\"}," +
                "\"body\":{\"placeholders\":%s}}," +
                "\"language\":\"%s\"}}]}",
                infobipFromNumber, toNumber, templateName, imageUrl, placeholdersJson, language
            );

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://" + infobipBaseUrl + "/whatsapp/1/message/template"))
                    .header("Authorization", "App " + infobipApiKey)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();

            if (response.statusCode() == 200 || response.statusCode() == 201) {
                // PENDING_ENROUTE = template pas encore approuvé → retourner false pour activer le fallback
                if (responseBody.contains("REJECTED") || responseBody.contains("rejected") ||
                    responseBody.contains("TEMPLATE_NOT_FOUND") || responseBody.contains("UNDELIVERABLE") ||
                    responseBody.contains("PENDING_ENROUTE")) {
                    log.warn("Template image {} non livré (en attente d'approbation): {}", templateName, responseBody);
                    return false;
                }
                log.info("✅ WhatsApp image [{}] envoyé à +{} | Réponse: {}", templateName, toNumber, responseBody);
                return true;
            } else {
                log.warn("Template image {} erreur {}: {}", templateName, response.statusCode(), responseBody);
                return false;
            }
        } catch (Exception e) {
            log.error("Erreur sendImageTemplate [{}]", templateName, e);
            return false;
        }
    }

    private void sendVoiceCall(String toNumber) {
        try {
            String texte = "Bonjour. Ceci est un rappel de vaccination. Votre enfant doit etre vaccine prochainement. Veuillez vous rendre au centre de sante. Merci.";

            // TTS v3/single attend une structure plate (pas messages:[])
            String voiceBody = String.format(
                "{\"from\":\"Vaccination\",\"to\":\"%s\"," +
                "\"text\":\"%s\"," +
                "\"language\":{\"languageCode\":\"fr\"}," +
                "\"voice\":{\"name\":\"Aurelie\",\"gender\":\"female\"}}",
                toNumber, texte
            );

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest voiceRequest = HttpRequest.newBuilder()
                    .uri(URI.create("https://" + infobipBaseUrl + "/tts/3/single"))
                    .header("Authorization", "App " + infobipApiKey)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(voiceBody))
                    .build();

            HttpResponse<String> voiceResponse = client.send(voiceRequest, HttpResponse.BodyHandlers.ofString());

            if (voiceResponse.statusCode() == 200 || voiceResponse.statusCode() == 201) {
                log.info("✅ Appel vocal envoyé avec succès vers +{}", toNumber);
            } else {
                log.error("Erreur appel vocal Infobip: {} - {}", voiceResponse.statusCode(), voiceResponse.body());
            }
        } catch (Exception e) {
            log.error("Erreur lors de l'appel vocal", e);
        }
    }
}
