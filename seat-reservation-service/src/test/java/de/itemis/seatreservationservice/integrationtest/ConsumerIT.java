package de.itemis.seatreservationservice.integrationtest;

import de.itemis.seatreservationservice.domain.AvailabilityResponse;
import de.itemis.seatreservationservice.service.PrinterService;
import de.itemis.seatreservationservice.service.SeatAvailabilityConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerIT {

    private static String trainId = "12";

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private SeatAvailabilityConsumer consumer;

    @MockBean
    private PrinterService service;

    @Test
    public void shouldParseAvailabilityResponse() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        consumer.setTestCountDownLatch(countDownLatch);

        AvailabilityResponse response = sendTestAvailabilityResponse();

        countDownLatch.await(5, TimeUnit.SECONDS);
        verify(service, times(1)).printResult(eq(response));
    }

    private AvailabilityResponse sendTestAvailabilityResponse() {
        AvailabilityResponse response = new AvailabilityResponse();
        response.setTrainId(trainId);
        response.setAvailableSeats(3);
        jmsTemplate.convertAndSend("seatAvailability", response);
        return response;
    }
}
