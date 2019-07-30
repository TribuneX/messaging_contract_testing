package de.itemis.seatreservationservice;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ActiveMQQueue createReservationQueue() {
        return new ActiveMQQueue("seatReservation");
    }
}
