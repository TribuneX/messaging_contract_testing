package de.itemis.seatreservationservice;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

import static org.mockito.Mockito.*;

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

        verify(jmsTemplate,times(1)).convertAndSend(anyString(),any(Object.class),any(MessagePostProcessor.class));
    }
}
