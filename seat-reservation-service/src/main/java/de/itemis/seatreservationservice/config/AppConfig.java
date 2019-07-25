package de.itemis.seatreservationservice.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean("seatReservation")
    ActiveMQQueue seatReservationRequestQueue() {
        return new ActiveMQQueue("seatReservation");
    }
}
