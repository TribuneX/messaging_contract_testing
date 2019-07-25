package de.itemis.seatreservationservice;

import org.apache.activemq.command.ActiveMQQueue;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.verifier.messaging.boot.AutoConfigureMessageVerifier;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureMessageVerifier
public abstract class MessagingBase {
    @Autowired
    JmsMessageVerifier messaging;
    @Autowired SeatReservationProducer seatReservationProducer;
    @Autowired
    ActiveMQQueue queue;

    @Before
    public void setup(){
    }

    public void testSend() {
        seatReservationProducer.send("12");
    }
}
