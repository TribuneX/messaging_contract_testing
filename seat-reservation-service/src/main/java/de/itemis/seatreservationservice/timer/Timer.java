package de.itemis.seatreservationservice.timer;

import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.HashMap;
import java.util.Map;

@Component
public class Timer {

    Map<String, StopWatch> stopWatches;

    public Timer() {
        this.stopWatches = new HashMap<>();
    }

    public void startNewTimer(String requestId) {
        StopWatch stopWatch = new StopWatch(requestId);
        stopWatch.start();
        stopWatches.put(requestId, stopWatch);
    }

    public double stopAndReturnTimer(String requestId) {
        StopWatch stopWatch = stopWatches.remove(requestId);
        if (stopWatch != null) {
            stopWatch.stop();
            return stopWatch.getTotalTimeSeconds();
        } else {
            return -1;
        }
    }
}
