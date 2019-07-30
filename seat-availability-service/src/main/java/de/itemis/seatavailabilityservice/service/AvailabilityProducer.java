package de.itemis.seatavailabilityservice.service;

import de.itemis.seatavailabilityservice.domain.AvailabilityResponse;
import de.itemis.seatavailabilityservice.processor.ReplyToProcessor;
import lombok.Setter;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Setter
public class AvailabilityProducer {

    private JmsTemplate jmsTemplate;
    private String serviceId;

    public AvailabilityProducer(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.serviceId = String.valueOf(new Random().nextInt(9999));
    }

    public void send(final String trainId, final int availableSeats, final String requestId) {
        AvailabilityResponse response = new AvailabilityResponse(trainId, availableSeats, requestId, serviceId);
        jmsTemplate.convertAndSend("seatAvailability", response, new ReplyToProcessor());
    }
}
