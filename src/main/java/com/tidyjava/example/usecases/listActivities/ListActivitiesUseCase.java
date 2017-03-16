package com.tidyjava.example.usecases.listActivities;

import com.tidyjava.example.callback.Callback;
import com.tidyjava.example.entities.Activity;
import com.tidyjava.example.gateways.ActivityGateway;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class ListActivitiesUseCase implements ListActivitiesInputBoundary {
    private final ActivityGateway activityGateway;

    @Inject
    public ListActivitiesUseCase(ActivityGateway activityGateway) {
        this.activityGateway = activityGateway;
    }

    @Override
    public void listActivities(ListActivitiesOutputBoundary presenter) {
        activityGateway.findAll(Callback.of(
                activities -> presenter.success(toResponseModel(activities)),
                presenter::failure));
    }

    private List<ActivityDetails> toResponseModel(List<Activity> activities) {
        return activities
                .stream()
                .map(Activity::getName)
                .map(ActivityDetails::new)
                .collect(Collectors.toList());
    }
}
