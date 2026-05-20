package com.gestionvaccination.appointmentservice.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/appointments")
public class TwilioVoiceController {

//    @GetMapping(value = "/twilio/voice", produces = "application/xml")
//    public String playAudio() {
//
//        return """
//                <?xml version="1.0" encoding="UTF-8"?>
//                <Response>
//                    <Play>https://nondeluded-brianna-pettedly.ngrok-free.dev/audios/wolof1.mp3</Play>
//                </Response>
//                """;
//    }


    @GetMapping(value = "/twilio/voice", produces = "text/xml")
    public String playAudio() {

        return """
    <Response>
        <Say language="fr-FR">Bonjour ceci est un test de rappel vaccinal</Say>
    </Response>
    """;
    }
}