package com.planner.planner.participant;

import com.planner.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository repository;

    public void registerParticipantsToEvent(List<String> participantsToInvite, Trip trip){
        List<Participant> participants = participantsToInvite.stream().map(email -> new Participant(trip, email)).toList();

        this.repository.saveAll(participants);
    }

    public ParticipantCreateResponse registerParticipantToEvent(String email, Trip trip) {
        Participant newPaticipant = new Participant(trip, email);

        this.repository.save(newPaticipant);

        return new ParticipantCreateResponse(newPaticipant.getId());
    }

    public void triggerConfirmationEmailToParticipants(UUID tripId){}
    public void triggerConfirmationEmailToParticipants(String email){}

    public List<ParticipantData> getAllParticipantsFromEvent(UUID id) {
        return this.repository.findByTripId(id).stream().map(participant -> new ParticipantData(participant.getId(), participant.getName(), participant.getEmail(), participant.getIsConfirmed())).toList();
    }
}
