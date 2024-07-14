package com.planner.planner.Activity;

import com.planner.planner.trip.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository repository;

    public ActivityResponse registerActivity(ActivitiRequestPaylod paylod, Trip trip) {
        Activity newActivity = new Activity(paylod.title(), paylod.accours_at(), trip);
        this.repository.save(newActivity);

        return new ActivityResponse(newActivity.getId());

    }

    public List<ActivityData> getAllActivitiesFromId(UUID tripId){
        return this.repository.findByTripId(tripId).stream().map(activity -> new ActivityData(activity.getId(), activity.getTitle(), activity.getOccursAt())).toList();
    }
}
