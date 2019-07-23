package de.itemis.seatavailabilityservice;

import de.itemis.seatavailabilityservice.domain.ReservationRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ConsumerIntegrationTest {

    private static String trainId = "12";

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    SeatReservationConsumer consumer;

    @MockBean
    private SeatAvailabilityService service;

    @Test
    public void shouldParseReservationRequest() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        consumer.setTestCountDownLatch(countDownLatch);
        sendTestReservationRequest();
        countDownLatch.await(60, TimeUnit.SECONDS);
        verify(service, times(1)).getFreeSeats(trainId);
    }

    private void sendTestReservationRequest() {
        ReservationRequest request = new ReservationRequest();
        request.setTrainId(trainId);
        jmsTemplate.convertAndSend("seatReservation", request);
    }
}
