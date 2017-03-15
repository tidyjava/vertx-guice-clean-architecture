package com.tidyjava.example.usecases.listActivities;

import com.tidyjava.example.entities.Activity;
import com.tidyjava.example.gateways.ActivityGateway;

import javax.inject.Inject;
import java.util.List;

public class ListActivitiesUseCase implements ListActivitiesInputBoundary {
    private final ActivityGateway activityGateway;

    @Inject
    public ListActivitiesUseCase(ActivityGateway activityGateway) {
        this.activityGateway = activityGateway;
    }

    @Override
    public void listActivities(ListActivitiesOutputBoundary presenter) {
        activityGateway.findAll(activities -> presenter.present(toResponseModel(activities)));
    }

    private ListActivitiesResponseModel toResponseModel(List<Activity> activities) {
        ListActivitiesResponseModel responseModel = new ListActivitiesResponseModel();
        for (Activity activity : activities) {
            ActivityDetails activityDetails = toActivityDetails(activity);
            responseModel.add(activityDetails);
        }
        return responseModel;
    }

    private ActivityDetails toActivityDetails(Activity activity) {
        ActivityDetails activityDetails = new ActivityDetails();
        activityDetails.name = activity.getName();
        return activityDetails;
    }
}
