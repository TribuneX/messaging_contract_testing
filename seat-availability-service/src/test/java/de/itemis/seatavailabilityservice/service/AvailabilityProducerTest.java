package de.itemis.seatavailabilityservice.service;

import de.itemis.seatavailabilityservice.domain.AvailabilityResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class AvailabilityProducerTest {

    @Mock
    private JmsTemplate jmsTemplate;

    @Test
    public void shouldSendMessageContainingTheAvailabilityResponse() {
        int availableSeats = 3;
        String trainId = "12";
        String requestId = "0815";
        String serviceId = "serviceId";
        AvailabilityProducer producer = new AvailabilityProducer(jmsTemplate);
        producer.setServiceId(serviceId);
        AvailabilityResponse response = new AvailabilityResponse(trainId, availableSeats, requestId, serviceId);

        producer.send(trainId, availableSeats, requestId);

        verify(jmsTemplate, times(1)).convertAndSend(eq("seatAvailability"), eq(response), any(MessagePostProcessor.class));
    }
}
