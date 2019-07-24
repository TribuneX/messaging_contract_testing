package de.itemis.seatavailabilityservice.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.itemis.seatavailabilityservice.domain.AvailabilityResponse;
import de.itemis.seatavailabilityservice.domain.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

//@Component
public class ReservationRequestConverter implements MessageConverter {

    private static final Logger LOGGER =
        LoggerFactory.getLogger(ReservationRequestConverter.class);

    ObjectMapper mapper;

    public ReservationRequestConverter() {
        mapper = new ObjectMapper();
    }

    @Override
    public Message toMessage(Object object, Session session)
        throws JMSException {
        ReservationRequest request = (ReservationRequest) object;
        String payload = null;
        try {
            payload = mapper.writeValueAsString(request);
            LOGGER.info("outbound json='{}'", payload);
        } catch (JsonProcessingException e) {
            LOGGER.error("error converting form request", e);
        }

        TextMessage message = session.createTextMessage();
        message.setText(payload);

        return message;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        String payload = textMessage.getText();
        LOGGER.info("inbound json='{}'", payload);

        ReservationRequest request = null;
        try {
            request = mapper.readValue(payload, ReservationRequest.class);
        } catch (Exception e) {
            LOGGER.error("error converting to request", e);
        }

        return request;
    }
}
