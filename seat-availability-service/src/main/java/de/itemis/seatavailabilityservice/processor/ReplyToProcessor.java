package de.itemis.seatavailabilityservice.processor;

import org.springframework.jms.core.MessagePostProcessor;

import javax.jms.JMSException;
import javax.jms.Message;

public class ReplyToProcessor implements MessagePostProcessor {

    public Message postProcessMessage(Message message) throws JMSException {
        message.setStringProperty("requiresReply", "no");
        return message;
    }
}
