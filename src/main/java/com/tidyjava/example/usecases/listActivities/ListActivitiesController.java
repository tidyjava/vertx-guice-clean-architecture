package com.tidyjava.example.usecases.listActivities;

import javax.inject.Inject;
import java.util.function.Supplier;

public class ListActivitiesController {
    private final ListActivitiesInputBoundary useCase;
    private final Supplier<ListActivitiesOutputBoundary> presenterFactory;

    @Inject
    public ListActivitiesController(ListActivitiesInputBoundary useCase, Supplier<ListActivitiesOutputBoundary> presenterFactory) {
        this.useCase = useCase;
        this.presenterFactory = presenterFactory;
    }

    public void handle(ListActivitiesView view) {
        ListActivitiesOutputBoundary presenter = presenterFactory.get();
        useCase.listActivities(presenter);
        view.generate(presenter.getViewModel());
    }
}
