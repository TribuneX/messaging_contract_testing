package de.itemis.seatreservationservice.service;

import de.itemis.seatreservationservice.domain.ReservationRequest;
import de.itemis.seatreservationservice.processor.ReplyToProcessor;
import de.itemis.seatreservationservice.timer.Timer;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.util.Random;

@Component
public class ReservationProducer {

    private JmsTemplate jmsTemplate;
    private ActiveMQQueue queue;
    private Timer timer;

    public ReservationProducer(final JmsTemplate jmsTemplate, @Qualifier("reservation") ActiveMQQueue queue, Timer timer) {
        this.jmsTemplate = jmsTemplate;
        this.queue = queue;
        this.timer = timer;
    }

    public Destination send(final String trainId) {
        ReservationRequest request = new ReservationRequest();
        request.setTrainId(trainId);
        String requestId = generateRequestId();
        request.setRequestId(requestId);
        timer.startNewTimer(requestId);
        ActiveMQQueue destination = new ActiveMQQueue(requestId);
        jmsTemplate.convertAndSend(queue, request, new ReplyToProcessor(destination));
        return destination;
    }

    private String generateRequestId() {
        int random = new Random().nextInt(99999);
        String generatedString = String.valueOf(random);
        return generatedString;
    }
}
