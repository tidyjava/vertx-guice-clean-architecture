package com.tidyjava.example.usecases.listActivities;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.tidyjava.example.gateways.ActivityGateway;
import com.tidyjava.example.gateways.ActivityGatewayImpl;
import io.vertx.core.Vertx;

import java.util.function.Supplier;

public class ListActivitiesModule extends AbstractModule {
    private final Vertx vertx;

    public ListActivitiesModule(Vertx vertx) {
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
        bind(new TypeLiteral<Supplier<ListActivitiesOutputBoundary>>() {})
                .toInstance(ListActivitiesPresenter::new);
    }
}
