package de.itemis.seatavailabilityservice.integrationtest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.itemis.seatavailabilityservice.domain.AvailabilityResponse;
import de.itemis.seatavailabilityservice.service.AvailabilityProducer;
import org.apache.activemq.command.ActiveMQQueue;
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
public class AvailabilityProducerIT {

    @Autowired
    private AvailabilityProducer producer;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void shouldSendMessageWithTrainId() throws JMSException, IOException {
        String trainId = "12";
        int availableSeats = 3;
        ActiveMQQueue queue = new ActiveMQQueue("seatAvailability");
        sendAvailabilityResponse(trainId, availableSeats, queue);

        jmsTemplate.setReceiveTimeout(1000);
        Message message = jmsTemplate.receive(queue);
        TextMessage textMessage = (TextMessage) message;

        AvailabilityResponse response = mapper.readValue(textMessage.getText(), AvailabilityResponse.class);
        assertThat(response.getTrainId()).isEqualTo(trainId);
        assertThat(response.getAvailableSeats()).isEqualTo(availableSeats);
    }

    private void sendAvailabilityResponse(final String trainId, final int availableSeats, final ActiveMQQueue queue) {
        String requestId = "0815";
        producer.send(trainId, availableSeats, requestId, queue);
    }
}
