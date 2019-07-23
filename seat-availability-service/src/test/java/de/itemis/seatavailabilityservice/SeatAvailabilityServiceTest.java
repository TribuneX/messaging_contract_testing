package de.itemis.seatavailabilityservice;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SeatAvailabilityServiceTest {

    @Test
    public void shouldReturnThreeFreeSeatsForEveryTrainID() {
        SeatAvailabilityService seatAvailabilityService = new SeatAvailabilityService();

        int freeSeats = seatAvailabilityService.getFreeSeats("doesNotMatter");

        assertThat(freeSeats).isEqualTo(3);
    }
}