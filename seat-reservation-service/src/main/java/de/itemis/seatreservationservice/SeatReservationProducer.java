package de.itemis.seatreservationservice;

import de.itemis.seatreservationservice.domain.ReservationRequest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SeatReservationProducer {

    private JmsTemplate jmsTemplate;

    public SeatReservationProducer(final JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(final String trainId) {
        ReservationRequest request = new ReservationRequest();
        request.setTrainId(trainId);

        jmsTemplate.convertAndSend("seatReservation", request, new ReplyToProcessor());
    }
}
