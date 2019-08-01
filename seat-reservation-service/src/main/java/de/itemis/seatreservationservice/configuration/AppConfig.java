package de.itemis.seatreservationservice.configuration;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean(name = "reservation")
    public ActiveMQQueue createReservationQueue() {
        return new ActiveMQQueue("seatReservation");
    }

    @Bean(name = "availability")
    public ActiveMQQueue createAvailabilityQueue() {
        return new ActiveMQQueue("seatAvailability");
    }
}
