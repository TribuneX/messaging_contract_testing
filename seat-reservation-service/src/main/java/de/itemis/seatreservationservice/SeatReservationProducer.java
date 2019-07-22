package de.itemis.seatreservationservice;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SeatReservationProducer {

    private JmsTemplate jmsTemplate;
    private static String QUEUE_NAME = "seatReservation";

    public SeatReservationProducer(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(final String trainId) {
        jmsTemplate.convertAndSend(QUEUE_NAME, trainId, new ReplyToProcessor());
    }
}
