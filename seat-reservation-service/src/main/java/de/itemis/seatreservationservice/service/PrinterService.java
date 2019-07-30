package de.itemis.seatreservationservice.service;

import de.itemis.seatreservationservice.domain.AvailabilityResponse;
import de.itemis.seatreservationservice.timer.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrinterService {

    @Autowired
    private Timer timer;

    private static final Logger LOGGER =
        LoggerFactory.getLogger(PrinterService.class);

    public void printResult(final AvailabilityResponse response) {
        double durationInSeconds = timer.stopAndReturnTimer(response.getRequestId());

        LOGGER.info(
            "Availability Report: train ID '{}', available seats '{}', request took '{}' seconds, was answered by '{}' ",
            response.getTrainId(),
            response.getAvailableSeats(),
            durationInSeconds,
            response.getServiceId()
        );
    }
}
