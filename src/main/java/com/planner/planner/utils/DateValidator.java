package com.planner.planner.utils;

import com.planner.planner.exception.TripException;

import java.time.LocalDateTime;

public class DateValidator {

    public void isValidDate(LocalDateTime startsAt, LocalDateTime endsAt) throws TripException {
        if (endsAt.isBefore(startsAt))
            throw new TripException("A data de fim não pode ser antes da data de inicio");
    }
}
