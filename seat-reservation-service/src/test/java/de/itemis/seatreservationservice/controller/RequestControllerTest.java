package de.itemis.seatreservationservice.controller;

import de.itemis.seatreservationservice.service.AvailabilityConsumer;
import de.itemis.seatreservationservice.service.ReservationProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.jms.JMSException;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RequestControllerTest {

    @Mock
    private AvailabilityConsumer consumer;

    @Mock
    private ReservationProducer producer;

    @Test
    public void shouldWaitForResponseAfterSending() throws IOException, JMSException {
        RequestController controller = new RequestController(producer, consumer);

        controller.sendRequest();

        verify(consumer, times(1)).receive(any());
    }
}
