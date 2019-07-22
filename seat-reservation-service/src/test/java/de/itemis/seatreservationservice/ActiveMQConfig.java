package de.itemis.seatreservationservice;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.jms.Queue;

@Configuration
public class ActiveMQConfig {
    public static final String QUEUE = "seatReservation";

    @Bean
    public Queue setupQueue() {
        return new ActiveMQQueue(QUEUE);
    }
}
