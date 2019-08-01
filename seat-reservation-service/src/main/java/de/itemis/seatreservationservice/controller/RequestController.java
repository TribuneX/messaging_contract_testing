package de.itemis.seatreservationservice.controller;

import de.itemis.seatreservationservice.domain.AvailabilityResponse;
import de.itemis.seatreservationservice.service.AvailabilityConsumer;
import de.itemis.seatreservationservice.service.ReservationProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.JMSException;
import java.io.IOException;

@RestController
public class RequestController {

    private ReservationProducer producer;
    private AvailabilityConsumer consumer;

    public RequestController(ReservationProducer producer, AvailabilityConsumer consumer) {
        this.producer = producer;
        this.consumer = consumer;
    }

    @GetMapping("/")
    public AvailabilityResponse sendRequest() throws IOException, JMSException {
        Destination tempQueue = producer.send("12");
        return consumer.receive(tempQueue);
    }
}
