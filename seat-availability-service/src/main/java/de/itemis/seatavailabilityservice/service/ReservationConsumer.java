package de.itemis.seatavailabilityservice.service;

import de.itemis.seatavailabilityservice.domain.ReservationRequest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import java.util.concurrent.CountDownLatch;

@Component
public class ReservationConsumer {

    private SeatAvailabilityService availabilityService;
    private CountDownLatch testCountDownLatch;

    private AvailabilityProducer producer;

    public ReservationConsumer(SeatAvailabilityService availabilityService, AvailabilityProducer producer) {
        this.availabilityService = availabilityService;
        this.producer = producer;
    }

    public void setTestCountDownLatch(CountDownLatch testCountDownLatch) {
        this.testCountDownLatch = testCountDownLatch;
    }

    @JmsListener(destination = "seatReservation")
    public void receiveMessage(ReservationRequest request, @Header(JmsHeaders.REPLY_TO) Destination replyTo) throws JMSException {
        String trainId = request.getTrainId();
        triggerCountdownLatch();
        int freeSeats = availabilityService.getFreeSeats(trainId);
        producer.send(trainId, freeSeats, request.getRequestId(), replyTo);
    }

    private void triggerCountdownLatch() {
        if (testCountDownLatch != null) {
            testCountDownLatch.countDown();
        }
    }
}
