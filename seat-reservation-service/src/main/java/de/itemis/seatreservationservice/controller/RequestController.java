package de.itemis.seatreservationservice.controller;

import de.itemis.seatreservationservice.service.ReservationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RequestController {

    @Autowired
    private ReservationProducer producer;

    @GetMapping("/")
    public void sendRequest() {
        producer.send("12");
    }
}
