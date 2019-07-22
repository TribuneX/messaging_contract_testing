package de.itemis.seatreservationservice;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProducerTest {

    private JmsTemplate jmsTemplate;

    @Before
    public void setUp() throws Exception {
        jmsTemplate = mock(JmsTemplate.class);
    }

    @Test
    public void shouldSendMessageWithTraindId() {
        SeatReservationProducer producer = new SeatReservationProducer(jmsTemplate);
        String trainId = "42";

        producer.send(trainId);

        verify(jmsTemplate).convertAndSend("seatReservation", trainId);
    }
}
