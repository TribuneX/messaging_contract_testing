package de.itemis.seatreservationservice.service;

import de.itemis.seatreservationservice.domain.AvailabilityResponse;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class SeatAvailabilityConsumer {

    private PrinterService printerService;
    private CountDownLatch countDownLatch;

    public SeatAvailabilityConsumer(final PrinterService printerService) {
        this.printerService = printerService;
    }

    public void setTestCountDownLatch(final CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @JmsListener(destination = "seatAvailability")
    public void receiveMessage(AvailabilityResponse response) {
        printerService.printResult(response);
        if (countDownLatch != null) {
            countDownLatch.countDown();
        }
    }
}
