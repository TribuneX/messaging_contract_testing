package de.itemis.seatavailabilityservice.verifier;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.itemis.seatavailabilityservice.domain.ReservationRequest;
import de.itemis.seatavailabilityservice.processor.ReplyToProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Primary
public class JmsMessageVerifier implements MessageVerifier {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    ObjectMapper mapper;

    @Override
    public void send(Object message, String destination) {
        jmsTemplate.convertAndSend(destination, message, new ReplyToProcessor());
    }

    @Override
    public Object receive(String destination, long timeout, TimeUnit timeUnit) {
        jmsTemplate.setReceiveTimeout(timeout);
        return receiveMessage(destination);
    }

    @Override
    public Object receive(String destination) {
        return receiveMessage(destination);
    }

    @Override
    public void send(Object payload, Map headers, String destination) {
        ReservationRequest request = null;
        try {
            request = mapper.readValue((String) payload, ReservationRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        jmsTemplate.convertAndSend(destination, request, new ReplyToProcessor());
    }

    private Object receiveMessage(String queueName) {
        Message message = jmsTemplate.receive(queueName);
        TextMessage textMessage = (TextMessage) message;
        try {
            return new GenericMessage<>(textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
    }
}
