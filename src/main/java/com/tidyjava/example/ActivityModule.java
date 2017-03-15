package com.tidyjava.example;

import com.google.inject.AbstractModule;
import com.tidyjava.example.gateways.ActivityGateway;
import com.tidyjava.example.gateways.ActivityGatewayImpl;
import com.tidyjava.example.usecases.listActivities.ListActivitiesInputBoundary;
import com.tidyjava.example.usecases.listActivities.ListActivitiesUseCase;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;

public class ActivityModule extends AbstractModule {
    private final Vertx vertx;
    private final JsonObject config;

    public ActivityModule(Vertx vertx, JsonObject config) {
        this.vertx = vertx;
        this.config = config;
    }

    @Override
    protected void configure() {
        bind(JDBCClient.class)
                .toInstance(JDBCClient.createShared(vertx, config));
        bind(ActivityGateway.class)
                .to(ActivityGatewayImpl.class);
        bind(ListActivitiesInputBoundary.class)
                .to(ListActivitiesUseCase.class);
    }
}
