package com.tidyjava.example.usecases.listActivities;

import com.tidyjava.example.callback.Callback;

import java.util.List;

public interface ListActivitiesInputBoundary {
    void listActivities(Callback<List<ActivityDetails>> presenter);
}
