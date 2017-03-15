package com.tidyjava.example.usecases.listActivities;

public class ListActivitiesPresenter implements ListActivitiesOutputBoundary {
    private String responseModel;

    @Override
    public void present(String responseModel) {
        this.responseModel = responseModel;
    }

    @Override
    public String getViewModel() {
        return responseModel;
    }
}
