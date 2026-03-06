package com.gestionvaccination.appointmentservice.service;

public interface AudioReminderService {

    void sendAudioReminder(String phoneNumber, String langue);
}