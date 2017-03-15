package com.tidyjava.example.usecases.listActivities;

import java.util.ArrayList;
import java.util.List;

public class ListActivitiesResponseModel {
    private List<ActivityDetails> activityDetails = new ArrayList<>();

    public List<ActivityDetails> getActivityDetails() {
        return activityDetails;
    }

    public void add(ActivityDetails activityDetails) {
        this.activityDetails.add(activityDetails);
    }
}
