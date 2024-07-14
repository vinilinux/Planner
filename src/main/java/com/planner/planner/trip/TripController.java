package com.planner.planner.trip;

import com.planner.planner.Activity.ActivitiRequestPaylod;
import com.planner.planner.Activity.ActivityData;
import com.planner.planner.Activity.ActivityResponse;
import com.planner.planner.Activity.ActivityService;
import com.planner.planner.exception.TripException;
import com.planner.planner.link.LinkData;
import com.planner.planner.link.LinkRequestPayload;
import com.planner.planner.link.LinkResponse;
import com.planner.planner.link.LinkService;
import com.planner.planner.participant.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    @Autowired
    private ParticipantService participantService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private LinkService linkService;

    @Autowired
    private TripService tripService;

    @PostMapping
    public ResponseEntity<?> createTrip(@RequestBody TripRequestPayload payload) {
        Trip newTrip;
        try {
            newTrip = tripService.registerTrip(payload);
        } catch (TripException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.EXPECTATION_FAILED);
        }


        this.participantService.registerParticipantsToEvent(payload.emails_to_invite(), newTrip);

        return ResponseEntity.ok(new TripCreateResponse(newTrip.getId()));

    }

    @GetMapping("/{id}")
    public ResponseEntity<Trip> getTripDetails(@PathVariable UUID id) {
        Optional<Trip> trip = tripService.findTripById(id);

        return trip.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTrip(@PathVariable UUID id, @RequestBody TripRequestPayload payload) {
        Optional<Trip> trip = tripService.findTripById(id);
        if (!trip.isPresent())
            return new ResponseEntity<>("Viagem não encontrado", HttpStatus.NOT_FOUND);

        try {
            return new ResponseEntity<>(tripService.updateTrip(trip, payload), HttpStatus.OK);
        } catch (TripException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
        }


    }

    @GetMapping("/{id}/confirm")
    public ResponseEntity<Trip> confirmTrip(@PathVariable UUID id) {
        Optional<Trip> trip = tripService.findTripById(id);

        if (trip.isPresent()) {
            this.participantService.triggerConfirmationEmailToParticipants(id);
            return ResponseEntity.ok(tripService.isConfirmed(trip));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/invite")
    public ResponseEntity<ParticipantCreateResponse> inviteParticipant(@PathVariable UUID id,  @RequestBody ParticipantRequestPayload payload) {
        Optional<Trip> trip = tripService.findTripById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            ParticipantCreateResponse participantCreateResponse = this.participantService.registerParticipantToEvent(payload.email(), rawTrip);

            if (rawTrip.getIsConfirmed()) this.participantService.triggerConfirmationEmailToParticipants(payload.email());

            return ResponseEntity.ok(participantCreateResponse);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{id}/participants")
    public ResponseEntity<List<ParticipantData>> getAllParticipants(@PathVariable UUID id) {
        List<ParticipantData> participantList = this.participantService.getAllParticipantsFromEvent(id);

        return ResponseEntity.ok(participantList);
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<ActivityResponse> registerActivity(@PathVariable UUID id,  @RequestBody ActivitiRequestPaylod payload) {
        Optional<Trip> trip = tripService.findTripById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            ActivityResponse activityResponse = this.activityService.registerActivity(payload, rawTrip);

            return ResponseEntity.ok(activityResponse);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{id}/activities")
    public ResponseEntity<List<ActivityData>> getAllActivities(@PathVariable UUID id) {
        List<ActivityData> activityDataList = this.activityService.getAllActivitiesFromId(id);

        return ResponseEntity.ok(activityDataList);
    }

    @PostMapping("/{id}/links")
    public ResponseEntity<LinkResponse> registerLink(@PathVariable UUID id, @RequestBody LinkRequestPayload payload) {
        Optional<Trip> trip = tripService.findTripById(id);

        if (trip.isPresent()) {
            Trip rawTrip = trip.get();

            LinkResponse linkResponse = linkService.registerLink(payload, rawTrip);

            return ResponseEntity.ok(linkResponse);
        }

        return ResponseEntity.notFound().build();

    }

    @GetMapping("/{id}/links")
    public ResponseEntity<List<LinkData>> getAllLinks(@PathVariable UUID id) {
        List<LinkData> linkDataList = this.linkService.getAllLinksFromId(id);

        return ResponseEntity.ok(linkDataList);
    }

}
