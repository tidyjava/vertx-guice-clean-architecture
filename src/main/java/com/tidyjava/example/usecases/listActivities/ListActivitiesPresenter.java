package com.tidyjava.example.usecases.listActivities;

import com.tidyjava.example.usecases.listActivities.ListActivitiesViewModel.ViewableActivityDetails;

public class ListActivitiesPresenter implements ListActivitiesOutputBoundary {
    private final ListActivitiesView listActivitiesView;

    public ListActivitiesPresenter(ListActivitiesView listActivitiesView) {
        this.listActivitiesView = listActivitiesView;
    }

    @Override
    public void present(ListActivitiesResponseModel responseModel) {
        ListActivitiesViewModel viewModel = new ListActivitiesViewModel();
        for (ActivityDetails activityDetails : responseModel.getActivityDetails()) {
            viewModel.add(makeViewable(activityDetails));
        }
        listActivitiesView.generate(viewModel);
    }

    private ViewableActivityDetails makeViewable(ActivityDetails activityDetails) {
        ViewableActivityDetails viewableActivityDetails = new ViewableActivityDetails();
        viewableActivityDetails.name = activityDetails.name;
        return viewableActivityDetails;
    }
}
