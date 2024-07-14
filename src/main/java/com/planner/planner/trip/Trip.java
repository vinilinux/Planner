package com.planner.planner.trip;

import com.planner.planner.exception.TripException;
import com.planner.planner.utils.DateValidator;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String destination;

    @Column(name = "starts_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime startsAt;

    @Column(name = "ends_at", nullable = false)
    @Setter(AccessLevel.NONE)
    private LocalDateTime endsAt;

    @Column(name = "is_confirmed", nullable = false)
    private Boolean isConfirmed;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "owner_email", nullable = false)
    private String ownerEmail;

    public Trip(TripRequestPayload data) throws TripException {
        this.destination = data.destination();
        this.isConfirmed = false;
        this.ownerEmail = data.owner_email();
        this.ownerName = data.owner_name();
    }

    public void setStartsAtAndEndsAt(LocalDateTime startsAt, LocalDateTime endsAt) throws TripException {
        DateValidator validation = new DateValidator();
        validation.isValidDate(startsAt, endsAt);
        this.startsAt = startsAt;
        this.endsAt = endsAt;

    }
}
