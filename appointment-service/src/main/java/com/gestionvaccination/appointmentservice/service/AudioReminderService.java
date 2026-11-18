package com.gestionvaccination.appointmentservice.service;

public interface AudioReminderService {

    void sendAudioReminder(String phoneNumber, String langue);

    // Méthode professionnelle avec nom du parent, enfant et vaccin
    default void sendAudioReminder(String phoneNumber, String langue,
                                   String prenomNomParent, String prenomNomEnfant, String vaccin) {
        sendAudioReminder(phoneNumber, langue); // fallback si non surchargée
    }

    // Crée le template WhatsApp professionnel via l'API Infobip (appel unique)
    default String createVaccinationTemplate() {
        return "Non implémenté";
    }
}