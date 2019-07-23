package de.itemis.seatavailabilityservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.itemis.seatavailabilityservice.domain.ReservationRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ConsumerTest {

    @Mock
    private SeatAvailabilityService seatAvailabilityService;
    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void shouldCallSeatAvailabilityCheckerOnRecievedRequest() throws IOException {
        String trainId = "12";
        ReservationRequest reservationRequest = createReservationRequest(trainId);
        SeatReservationConsumer consumer = new SeatReservationConsumer(mapper, seatAvailabilityService);

        consumer.receiveMessage(reservationRequest);

        verify(seatAvailabilityService,times(1)).getFreeSeats(trainId);
    }

    private ReservationRequest createReservationRequest(String trainId) throws JsonProcessingException {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setTrainId(trainId);
        return reservationRequest;
    }
}
