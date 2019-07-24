package de.itemis.seatreservationservice;

import de.itemis.seatreservationservice.domain.AvailabilityResponse;
import org.springframework.stereotype.Component;

@Component
public class PrinterService {

    public void printResult(final AvailabilityResponse response) {
        System.out.printf("Availability Report: train ID %s, available seats %s", response.getTrainId(), response.getAvailableSeats());
    }
}
