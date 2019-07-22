package de.itemis.seatreservationservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProducerIntegrationTest {

    @Autowired
    private SeatReservationProducer producer;

    @Autowired
    private JmsTemplate jmsTemplate;


    @Test
    public void shouldSendMessageToCorrectQueue() throws JMSException {
        String trainId = "12";
        producer.send(trainId);

        Message message = jmsTemplate.receive("seatReservation");
        TextMessage textMessage = (TextMessage) message;

        assertThat(textMessage.getText()).isEqualTo(trainId);
    }



}
