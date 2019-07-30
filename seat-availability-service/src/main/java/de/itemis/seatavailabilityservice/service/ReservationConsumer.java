package de.itemis.seatavailabilityservice.service;

import de.itemis.seatavailabilityservice.domain.ReservationRequest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

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
    public void receiveMessage(ReservationRequest request) {
        String trainId = request.getTrainId();
        triggerCountdownLatch();
        int freeSeats = availabilityService.getFreeSeats(trainId);
        producer.send(trainId, freeSeats, request.getRequestId());
    }

    private void triggerCountdownLatch() {
        if (testCountDownLatch != null) {
            testCountDownLatch.countDown();
        }
    }
}
