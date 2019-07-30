package de.itemis.seatreservationservice.service;

import de.itemis.seatreservationservice.timer.Timer;
import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

import static org.mockito.Mockito.*;

public class ProducerTest {

    private JmsTemplate jmsTemplate;
    private ActiveMQQueue queue;

    @Before
    public void setUp() throws Exception {
        jmsTemplate = mock(JmsTemplate.class);
        queue = mock(ActiveMQQueue.class);
    }

    @Test
    public void shouldSendMessageWithTraindId() {
        ReservationProducer producer = new ReservationProducer(jmsTemplate, queue, mock(Timer.class));
        String trainId = "42";

        producer.send(trainId);

        verify(jmsTemplate, times(1)).convertAndSend(eq(queue), any(Object.class), any(MessagePostProcessor.class));
    }
}
