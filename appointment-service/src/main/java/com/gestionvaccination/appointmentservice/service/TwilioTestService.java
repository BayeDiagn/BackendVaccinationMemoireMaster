package com.gestionvaccination.appointmentservice.service;

import com.twilio.rest.api.v2010.account.Call;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class TwilioTestService {

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public String makeTestCall(String phoneNumber) {

        try {

            Call call = Call.creator(
                    new PhoneNumber(phoneNumber),
                    new PhoneNumber(twilioPhoneNumber),
                    new URI("http://demo.twilio.com/docs/voice.xml")
            ).create();

            return "Call lancé. SID : " + call.getSid();

        } catch (Exception e) {
            return "Erreur : " + e.getMessage();
        }
    }
}