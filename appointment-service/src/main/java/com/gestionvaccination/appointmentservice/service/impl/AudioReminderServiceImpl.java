//package com.gestionvaccination.appointmentservice.service.impl;
//
//import com.gestionvaccination.appointmentservice.service.AudioReminderService;
//// Twilio désactivé (solde épuisé) - remplacé par InfobipReminder
//// import com.twilio.rest.api.v2010.account.Call;
//// import com.twilio.type.PhoneNumber;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//// import org.springframework.stereotype.Service;
//
//import java.net.URI;
//
//// @Service — désactivé pour éviter le conflit avec InfobipReminder
//@Slf4j
//public class AudioReminderServiceImpl implements AudioReminderService {
//
//    @Value("${twilio.phone.number}")
//    private String twilioPhoneNumber;
//
//    @Value("${audio.url.base}")
//    private String audioBaseUrl;
//
//    @Override
//    public void sendAudioReminder(String phoneNumber, String langue) {
//
//        try {
//
//            String audioFile = "";
//
//            switch (langue.toUpperCase()) {
//
//                case "WOLOF":
//                    audioFile = "wolof1.mp3";
//                    break;
//
//                default:
//                    log.warn("Langue non supportée : {}", langue);
//                    return;
//            }
//
//            // ✅ Correction ici
//            String audioUrl = audioBaseUrl + "/" + audioFile;
//
//            log.info("Appel vers {} avec audio {}", phoneNumber, audioUrl);
//
//            Call call = Call.creator(
//                    new PhoneNumber("+221" + phoneNumber),
//                    new PhoneNumber(twilioPhoneNumber),
//                    new URI("https://nondeluded-brianna-pettedly.ngrok-free.dev/twilio/voice")
//            ).create();
//
//            log.info("Appel lancé avec succès. SID: {}", call.getSid());
//
//        } catch (Exception e) {
//            log.error("Erreur lors de l'envoi du rappel audio", e);
//        }
//    }
//}