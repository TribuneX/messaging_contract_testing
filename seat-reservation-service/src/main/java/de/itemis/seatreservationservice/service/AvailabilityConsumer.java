package de.itemis.seatreservationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.itemis.seatreservationservice.domain.AvailabilityResponse;
import de.itemis.seatreservationservice.timer.Timer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.io.IOException;

@Component
public class AvailabilityConsumer {

    private JmsTemplate template;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private Timer timer;

    public AvailabilityConsumer(JmsTemplate template) {
        this.template = template;
    }

    public AvailabilityResponse receive(Destination destination) throws JMSException, IOException {
        TextMessage message = (TextMessage) template.receive(destination);
        AvailabilityResponse response = parseAvailabilityResponse(message);
        return response;
    }

    private AvailabilityResponse parseAvailabilityResponse(final TextMessage message) throws IOException, JMSException {
        AvailabilityResponse response = mapper.readValue(message.getText(), AvailabilityResponse.class);
        response = addRequestDuration(response);
        return response;
    }

    private AvailabilityResponse addRequestDuration(final AvailabilityResponse response) {
        double duration = timer.stopAndReturnTimer(response.getRequestId());
        response.setDuration(duration);
        return response;
    }
}
