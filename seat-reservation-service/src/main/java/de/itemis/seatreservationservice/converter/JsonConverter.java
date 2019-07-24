package de.itemis.seatreservationservice.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.itemis.seatreservationservice.domain.AvailabilityResponse;
import de.itemis.seatreservationservice.domain.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.io.IOException;

@Component
public class JsonConverter implements MessageConverter {

    private static final Logger LOGGER =
        LoggerFactory.getLogger(JsonConverter.class);

    ObjectMapper mapper;

    public JsonConverter() {
        mapper = new ObjectMapper();
    }

    @Override
    public Message toMessage(Object object, Session session)
        throws JMSException {

        String payload = null;

        try {
            payload = mapper.writeValueAsString(object);
            LOGGER.info("outbound json='{}'", payload);
        } catch (JsonProcessingException e) {
            LOGGER.error("error converting form response", e);
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

        if (payload.contains("availableSeats")){
            AvailabilityResponse response = null;
            try {
                response = mapper.readValue(payload, AvailabilityResponse.class);
            } catch (IOException e) {
                LOGGER.error("error converting to response", e);
            }
            return response;
        } else {
            ReservationRequest request = null;
            try {
                request = mapper.readValue(payload, ReservationRequest.class);
            } catch (IOException e) {
                LOGGER.error("error converting to request", e);
            }
            return request;
        }
    }
}
