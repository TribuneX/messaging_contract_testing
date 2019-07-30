package de.itemis.seatavailabilityservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityResponse {

    private String trainId;
    private int availableSeats;
    private String requestId;
    private String serviceId;
}
