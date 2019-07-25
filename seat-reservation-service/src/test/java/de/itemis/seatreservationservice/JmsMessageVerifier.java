package de.itemis.seatreservationservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.contract.verifier.messaging.MessageVerifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@Qualifier("JmsMessageVerifier")
public class JmsMessageVerifier implements MessageVerifier {

    @Autowired
    ActiveMQQueue queue;

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    ObjectMapper mapper;

    @Override
    public void send(Object message, String destination) {
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

    }

    private Object receiveMessage() {
        Message message = jmsTemplate.receive(queue);
        TextMessage textMessage = (TextMessage) message;
        try {
            return new GenericMessage<String>(textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
            return null;
        }
        /*try {
            return mapper.readValue(textMessage.getText(), ReservationRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;*/
    }
}
