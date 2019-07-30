package de.itemis.seatreservationservice.integrationtest;

import de.itemis.seatreservationservice.service.ReservationProducer;
import de.itemis.seatreservationservice.verifier.JmsMessageVerifier;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public abstract class MessagingBaseIT {

    @Autowired
    JmsMessageVerifier messaging;
    @Autowired
    ReservationProducer reservationProducer;

    public void testSend() {
        reservationProducer.send("12");
    }
}
