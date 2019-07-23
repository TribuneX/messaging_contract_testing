package de.itemis.seatavailabilityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class SeatAvailabilityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeatAvailabilityServiceApplication.class, args);
    }

}
