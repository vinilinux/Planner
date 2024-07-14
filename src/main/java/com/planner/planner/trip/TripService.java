package com.planner.planner.trip;

import com.planner.planner.exception.TripException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TripService {

    @Autowired
    private TripRepository repository;

    public Trip registerTrip(TripRequestPayload data) throws TripException {

        Trip newTrip = new Trip(data);
        newTrip.setStartsAtAndEndsAt(convertion(data.starts_at()), convertion(data.ends_at()));

        return repository.save(newTrip);
    }

    public Optional<Trip> findTripById(UUID id) {
        return repository.findById(id);
    }

    public Trip updateTrip(Optional<Trip> trip, TripRequestPayload data) throws TripException {

        Trip newTrip = trip.get();
        newTrip.setStartsAtAndEndsAt(convertion(data.starts_at()), convertion(data.ends_at()));
        newTrip.setDestination(data.destination());

        return repository.save(newTrip);

    }

    private LocalDateTime convertion(String startsAt) throws TripException {
        try {
            return LocalDateTime.parse(startsAt, DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new TripException("Formato invalido");
        }
    }

    public Trip isConfirmed(Optional<Trip> trip) {
        Trip rawTrip = trip.get();

        rawTrip.setIsConfirmed(true);

        return repository.save(rawTrip);

    }



}
