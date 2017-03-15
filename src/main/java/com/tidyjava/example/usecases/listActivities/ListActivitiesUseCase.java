package com.tidyjava.example.usecases.listActivities;

import com.tidyjava.example.gateways.ActivityGateway;

import javax.inject.Inject;

public class ListActivitiesUseCase implements ListActivitiesInputBoundary {
    private final ActivityGateway activityGateway;

    @Inject
    public ListActivitiesUseCase(ActivityGateway activityGateway) {
        this.activityGateway = activityGateway;
    }

    @Override
    public void listActivities(ListActivitiesOutputBoundary presenter) {
        activityGateway.findAll(ignored -> {});
        presenter.present("Use case is working!");
    }
}
