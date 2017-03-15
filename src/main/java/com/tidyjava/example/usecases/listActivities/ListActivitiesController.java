package com.tidyjava.example.usecases.listActivities;

import javax.inject.Inject;

public class ListActivitiesController {
    private final ListActivitiesInputBoundary useCase;
    private final ListActivitiesOutputBoundaryFactory presenterFactory;

    @Inject
    public ListActivitiesController(ListActivitiesInputBoundary useCase, ListActivitiesOutputBoundaryFactory presenterFactory) {
        this.useCase = useCase;
        this.presenterFactory = presenterFactory;
    }

    public void handle(ListActivitiesView view) {
        ListActivitiesOutputBoundary presenter = presenterFactory.create(view);
        useCase.listActivities(presenter);
    }
}
