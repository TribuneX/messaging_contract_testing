package de.itemis.seatreservationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.itemis.seatreservationservice.domain.ReservationRequest;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Primary
public class JmsMessageVerifier implements MessageVerifier {

    @Autowired
    ActiveMQQueue seatReservationRequestQueue;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    ObjectMapper mapper;

    @Override
    public void send(Object message, String destination) {
        sendRequest();
    }

    @Override
    public Object receive(String destination, long timeout, TimeUnit timeUnit) {
        return receiveMessage();
    }

    @Override
    public Object receive(String destination) {
        return receiveMessage();
    }

    @Override
    public void send(Object payload, Map headers, String destination) {
        sendRequest();
    }

    private void sendRequest() {
        ReservationRequest request = new ReservationRequest();
        request.setTrainId("12");
        jmsTemplate.convertAndSend(seatReservationRequestQueue, request, new ReplyToProcessor());
    }

    private Object receiveMessage() {
        Message message = jmsTemplate.receive(seatReservationRequestQueue);
        TextMessage textMessage = (TextMessage) message;
        try {
            return new GenericMessage<>(textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
    }
}
