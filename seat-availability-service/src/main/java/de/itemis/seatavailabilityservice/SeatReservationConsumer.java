package de.itemis.seatavailabilityservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.itemis.seatavailabilityservice.domain.ReservationRequest;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class SeatReservationConsumer {

    private ObjectMapper mapper;
    private SeatAvailabilityService availabilityService;
    private CountDownLatch testCountDownLatch;

    public SeatReservationConsumer(ObjectMapper mapper, SeatAvailabilityService availabilityService) {
        this.mapper = mapper;
        this.availabilityService = availabilityService;
    }

    public void setTestCountDownLatch(CountDownLatch testCountDownLatch){
        this.testCountDownLatch = testCountDownLatch;
    }

    @JmsListener(destination = "seatReservation")
    public void receiveMessage(ReservationRequest request) {
        availabilityService.getFreeSeats(request.getTrainId());
        if(testCountDownLatch != null) {
            testCountDownLatch.countDown();
        }
    }
}
