package com.tidyjava.example.usecases.listActivities;

import java.util.List;

public interface ListActivitiesView {
    void generate(List<ActivityDetails> responseModel);
}
