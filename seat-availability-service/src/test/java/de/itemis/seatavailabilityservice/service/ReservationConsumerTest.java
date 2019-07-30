package de.itemis.seatavailabilityservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.itemis.seatavailabilityservice.domain.ReservationRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ReservationConsumerTest {

    @Mock
    private SeatAvailabilityService seatAvailabilityService;

    @Mock
    private AvailabilityProducer producer;

    @Test
    public void shouldCallSeatAvailabilityCheckerOnRecievedRequest() throws IOException {
        String trainId = "12";
        ReservationRequest reservationRequest = createReservationRequest(trainId);
        ReservationConsumer consumer = new ReservationConsumer(seatAvailabilityService, producer);

        consumer.receiveMessage(reservationRequest);

        verify(seatAvailabilityService, times(1)).getFreeSeats(trainId);
    }

    private ReservationRequest createReservationRequest(String trainId) throws JsonProcessingException {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setTrainId(trainId);
        return reservationRequest;
    }
}
