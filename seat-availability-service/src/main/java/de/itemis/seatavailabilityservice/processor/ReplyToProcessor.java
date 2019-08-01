package de.itemis.seatavailabilityservice.processor;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.MessagePostProcessor;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

public class ReplyToProcessor implements MessagePostProcessor {

    private Destination destination;

    public ReplyToProcessor(Destination destination) {
        this.destination = destination;
    }

    public Message postProcessMessage(Message message) throws JMSException {
        message.setStringProperty("requiresReply", "no");
        message.setJMSReplyTo(new ActiveMQQueue("reply"));
        return message;
    }
}
