package de.itemis.seatreservationservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.Request;
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

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldSendMessageToCorrectQueue() throws JMSException, JsonProcessingException {
        String trainId = "12";
        producer.send(trainId);

        jmsTemplate.setReceiveTimeout(1000);
        Message message = jmsTemplate.receive("seatReservation");
        TextMessage textMessage = (TextMessage) message;

        assertThat(textMessage.getText()).isEqualTo(getRequestAsJson(trainId));
    }

    private String getRequestAsJson(final String trainId) throws JsonProcessingException {
        ReservationRequest reservationRequest = new ReservationRequest(trainId);
        return mapper.writeValueAsString(reservationRequest);
    }
}
