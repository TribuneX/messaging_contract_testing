package de.itemis.seatreservationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.itemis.seatreservationservice.domain.ReservationRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;

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
    public void shouldSendMessageWithTrainId() throws JMSException, IOException {
        String trainId = "12";
        producer.send(trainId);

        jmsTemplate.setReceiveTimeout(1000);
        Message message = jmsTemplate.receive("seatReservation");
        TextMessage textMessage = (TextMessage) message;

        ReservationRequest request = mapper.readValue(textMessage.getText(), ReservationRequest.class);
        assertThat(request.getTrainId()).isEqualTo(trainId);
    }
}

