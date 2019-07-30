package de.itemis.seatreservationservice.service;

import de.itemis.seatreservationservice.domain.ReservationRequest;
import de.itemis.seatreservationservice.processor.ReplyToProcessor;
import de.itemis.seatreservationservice.timer.Timer;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Random;

@Component
public class ReservationProducer {

    private JmsTemplate jmsTemplate;
    private ActiveMQQueue queue;
    private Timer timer;

    public ReservationProducer(final JmsTemplate jmsTemplate, ActiveMQQueue queue, Timer timer) {
        this.jmsTemplate = jmsTemplate;
        this.queue = queue;
        this.timer = timer;
    }

    public void send(final String trainId) {
        ReservationRequest request = new ReservationRequest();
        request.setTrainId(trainId);
        String requestId = generateRequestId();
        request.setRequestId(requestId);
        timer.startNewTimer(requestId);
        jmsTemplate.convertAndSend(queue, request, new ReplyToProcessor());
    }

    private String generateRequestId() {
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, Charset.forName("UTF-8"));

        return generatedString;
    }
}
