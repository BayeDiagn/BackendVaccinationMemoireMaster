package com.gestionvaccination.appointmentservice.controller;

import com.gestionvaccination.appointmentservice.service.TwilioTestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class TwilioTestController {

    private final TwilioTestService twilioTestService;

    @GetMapping("/api/test/call")
    public String testCall(@RequestParam String phone) {

        return twilioTestService.makeTestCall(phone);

    }



}