package com.tidyjava.example.usecases.listActivities;

import com.tidyjava.example.callback.Callback;
import com.tidyjava.example.entities.Activity;
import com.tidyjava.example.gateways.ActivityGateway;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ListActivitiesUseCase implements ListActivitiesInputBoundary {
    private final ActivityGateway activityGateway;

    @Inject
    public ListActivitiesUseCase(ActivityGateway activityGateway) {
        this.activityGateway = activityGateway;
    }

    @Override
    public void listActivities(Callback<List<ActivityDetails>> presenter) {
        activityGateway.findAll(Callback.of(
                activities -> presenter.success(toResponseModel(activities)),
                presenter::failure));
    }

    private List<ActivityDetails> toResponseModel(List<Activity> activities) {
        List<ActivityDetails> responseModel = new ArrayList<>();
        for (Activity activity : activities) {
            ActivityDetails activityDetails = new ActivityDetails(activity.getName());
            responseModel.add(activityDetails);
        }
        return responseModel;
    }
}
