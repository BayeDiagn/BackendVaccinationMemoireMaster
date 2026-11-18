package com.gestionvaccination.appointmentservice.service.impl;

import com.gestionvaccination.appointmentservice.client.dto.EnfantDTO;
import com.gestionvaccination.appointmentservice.client.dto.UtilisateurDTO;
import com.gestionvaccination.appointmentservice.entity.Appointment;
import com.gestionvaccination.appointmentservice.enumeration.StatutRv;
import com.gestionvaccination.appointmentservice.repository.AppointmentRepository;
import com.gestionvaccination.appointmentservice.service.AppointmentService;

import com.gestionvaccination.appointmentservice.service.AudioReminderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet; // ★ AJOUTÉ
import java.util.List;
import java.util.Set; // ★ AJOUTÉ

@Component
@RequiredArgsConstructor
@Slf4j
public class VaccinationReminderScheduler {

    private final AppointmentRepository appointmentRepository;
    private final AppointmentService appointmentService;
    private final EmailService emailService;
    private final AudioReminderService audioReminderService;

    // ★ AJOUTÉ : mémorise les IDs des rendez-vous déjà notifiés pour éviter les doublons
    private final Set<Long> alreadyNotified = new HashSet<>();

    /**
     * Planificateur qui s'exécute tous les jours à 13h40
     * Cette méthode envoie les rappels pour les rendez-vous à venir
     */
//    @Scheduled(cron = "0 45 01 * * ?")
    @Scheduled(cron = "0 * * * * ?")
    public void sendVaccinationReminders() {
        log.info("Démarrage de l'envoi des rappels à {}", LocalDateTime.now());

        // Envoyer des rappels pour aujourd'hui
        sendRemindersForDate(LocalDate.now(), 0);

        // Envoyer des rappels pour demain
        sendRemindersForDate(LocalDate.now().plusDays(1), 1);

        // Envoyer des rappels pour après-demain
        sendRemindersForDate(LocalDate.now().plusDays(2), 2);

        log.info("Fin de l'envoi des rappels à {}", LocalDateTime.now());
    }

    private void sendRemindersForDate(LocalDate date, int daysRemaining) {
        log.info("Envoi des rappels pour les vaccinations du {}", date);

        List<Appointment> appointments = appointmentRepository.findByDateAndStatut(date, StatutRv.EN_ATTENTE);

        log.info("Trouvé {} rendez-vous pour le {}", appointments.size(), date);

        for (Appointment appointment : appointments) {
            try {
                log.info("Traitement du rendez-vous ID: {}, enfantId: {}, userId: {}",
                        appointment.getId(), appointment.getEnfantId(), appointment.getUserId());

                // Récupérer les détails de l'enfant et du parent
                Appointment enrichedAppointment = appointmentService.enrichAppointment(appointment);

                EnfantDTO enfant = enrichedAppointment.getEnfant();
                UtilisateurDTO parent = enrichedAppointment.getUtilisateur();

                if (enfant != null && parent != null && parent.getEmail() != null) {
                    log.info("Envoi du rappel à {} pour l'enfant {} {}",
                            parent.getEmail(), enfant.getPrenom(), enfant.getNom());

//                    emailService.sendVaccinationReminder(
//                            enfant.getParent().getEmail(),
//                            parent.getNom(),
//                            enfant.getPrenom(),
//                            enfant.getNom(),
//                            appointment.getDate(),
//                            appointment.getNomVaccinAEffectuer(),
//                            daysRemaining
//                    );

                    log.info("Rappel envoyé avec succès");

//                    if(parent.getLangueMaternelle() != null){
//
//                        audioReminderService.sendAudioReminder(
//                                parent.getTelephone(),
//                                parent.getLangueMaternelle().name()
//                        );
//
//                    }

                    // ★ AJOUTÉ : vérifier si ce rendez-vous a déjà reçu une notification
                    if (!alreadyNotified.contains(appointment.getId())) {
                        // ★ AJOUTÉ : utilisation du vrai nom du parent, enfant et vaccin
                        String nomParent = parent.getPrenom() + " " + parent.getNom();
                        String nomEnfant = enfant.getPrenom() + " " + enfant.getNom();
                        String vaccin = appointment.getNomVaccinAEffectuer() != null
                                ? appointment.getNomVaccinAEffectuer() : "vaccin";

                        // ★ NOUVEAUTÉ : envoi email HTML professionnel au parent
                        // Design avec couleurs vertes, logo, date encadrée — déjà développé dans EmailService
                        emailService.sendVaccinationReminder(
                                parent.getEmail(),
                                parent.getNom(),
                                enfant.getPrenom(),
                                enfant.getNom(),
                                appointment.getDate(),
                                vaccin,
                                daysRemaining
                        );

                        // ★ AJOUTÉ : envoi WhatsApp Infobip (image bébé + emojis pour mamans analphabètes)
                        audioReminderService.sendAudioReminder(
                                "782944658", "WOLOF", nomParent, nomEnfant, vaccin);

                        alreadyNotified.add(appointment.getId()); // ★ AJOUTÉ : marquer comme notifié
                        log.info("Rendez-vous ID {} marqué comme notifié (email + WhatsApp)", appointment.getId()); // ★ AJOUTÉ
                    } else {
                        log.info("Rendez-vous ID {} déjà notifié, envoi ignoré", appointment.getId()); // ★ AJOUTÉ
                    }

                } else {
                    log.warn("Données manquantes pour le rendez-vous ID: {}", appointment.getId());
                    if (parent == null) log.warn("Parent manquant");
                    else if (parent.getEmail() == null) log.warn("Email du parent manquant");
                    if (enfant == null) log.warn("Enfant manquant");
                }
            } catch (Exception e) {
                log.error("Erreur lors de l'envoi du rappel pour le rendez-vous ID: {}", appointment.getId(), e);
                e.printStackTrace(); // Pour avoir la trace complète de l'erreur
            }
        }
    }

    /**
     * Méthode pour tests uniquement - s'exécute toutes les minutes
     * Décommentez cette méthode pour tester la fonctionnalité
     */
    /*
//    @Scheduled(cron = "0 * * * * ?")
//    public void testVaccinationReminders() {
//        log.info("TEST - Démarrage de l'envoi des rappels à {}", LocalDateTime.now());
//        sendVaccinationReminders();
//        log.info("TEST - Fin de l'envoi des rappels");
//    }
//    */
//
//
//    @Scheduled(cron = "0 * * * * ?")
    public void testCall() {

        log.info("Test appel Twilio");

        audioReminderService.sendAudioReminder(
                "+221782944658",
                "WOLOF"
        );

    }
}