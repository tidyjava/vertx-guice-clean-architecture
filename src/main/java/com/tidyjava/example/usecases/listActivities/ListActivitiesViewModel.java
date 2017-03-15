package com.tidyjava.example.usecases.listActivities;

import java.util.ArrayList;
import java.util.List;

public class ListActivitiesViewModel {
    private List<ViewableActivityDetails> viewableActivityDetails = new ArrayList<>();

    public void add(ViewableActivityDetails viewableActivityDetails) {
        this.viewableActivityDetails.add(viewableActivityDetails);
    }

    public List<ViewableActivityDetails> getViewableActivityDetails() {
        return viewableActivityDetails;
    }

    public static class ViewableActivityDetails {
        public String name;

        @Override
        public String toString() {
            return "ViewableActivityDetails{" +
                "name='" + name + '\'' +
                '}';
        }
    }
}
