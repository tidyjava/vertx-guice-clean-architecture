package com.tidyjava.example.usecases.listActivities;

public class ListActivitiesOutputBoundaryFactoryImpl implements ListActivitiesOutputBoundaryFactory {
    @Override
    public ListActivitiesOutputBoundary create(ListActivitiesView listActivitiesView) {
        return new ListActivitiesPresenter(listActivitiesView);
    }
}
