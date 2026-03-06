package com.gestionvaccination.appointmentservice.service.impl;

import com.gestionvaccination.appointmentservice.service.AudioReminderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
@Slf4j
public class AudioReminderServiceImpl implements AudioReminderService {

    @Override
    public void sendAudioReminder(String phoneNumber, String langue) {

        try {

            String audioFile = "";

            switch (langue.toUpperCase()) {

                case "WOLOF":
                    audioFile = "audios/wolof.mpeg";
                    break;

//                case "PULAAR":
//                    audioFile = "audios/wolof.mpeg";
//                    break;
//
//                case "SERERE":
//                    audioFile = "audios/wolof.mpeg";
//                    break;
//
//                case "FRANCAIS":
//                    audioFile = "audios/wolof.mpeg";
//                    break;

                default:
                    log.warn("Langue non supportée pour audio : {}", langue);
                    return;
            }

            InputStream audioStream = getClass()
                    .getClassLoader()
                    .getResourceAsStream(audioFile);

            if (audioStream == null) {
                log.error("Fichier audio introuvable : {}", audioFile);
                return;
            }

            log.info("Audio trouvé : {}", audioFile);
            log.info("Simulation envoi audio au numéro : {}", phoneNumber);

            // Simulation uniquement pour le moment
            // Ici plus tard on branchera WhatsApp ou appel vocal

        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de l'audio", e);
        }
    }
}