package de.itemis.seatreservationservice.timer;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TimerTest {

    @Test
    public void shouldStopTimer() throws InterruptedException {
        Timer timer = new Timer();
        String requestId = "0815";
        timer.startNewTimer(requestId);

        Thread.sleep(100);
        double durationInSeconds = timer.stopAndReturnTimer(requestId);

        assertThat(durationInSeconds).isGreaterThan(0.0);
    }

    @Test
    public void shouldAcceptToStopNonExistingTimer() {
        Timer timer = new Timer();
        double durationInSeconds = timer.stopAndReturnTimer("not_available");

        assertThat(durationInSeconds).isEqualTo(-1);
    }
}
