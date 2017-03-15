package com.tidyjava.example;

import com.google.inject.AbstractModule;
import com.tidyjava.example.gateways.ActivityGateway;
import com.tidyjava.example.gateways.ActivityGatewayImpl;
import com.tidyjava.example.usecases.listActivities.ListActivitiesInputBoundary;
import com.tidyjava.example.usecases.listActivities.ListActivitiesUseCase;
import io.vertx.core.Vertx;

public class ActivityModule extends AbstractModule {
    private final Vertx vertx;

    public ActivityModule(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    protected void configure() {
        bind(Vertx.class)
            .toInstance(vertx);
        bind(ActivityGateway.class)
            .to(ActivityGatewayImpl.class);
        bind(ListActivitiesInputBoundary.class)
            .to(ListActivitiesUseCase.class);
    }
}
