package com.tidyjava.example.usecases.listActivities;

public interface ListActivitiesOutputBoundary {
    void present(String responseModel);

    String getViewModel();
}
