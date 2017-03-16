package com.tidyjava.example.usecases.listActivities;

import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

import java.util.List;

public class ListActivitiesView implements ListActivitiesOutputBoundary {
    private final RoutingContext ctx;

    public ListActivitiesView(RoutingContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void success(List<ActivityDetails> activityDetailsList) {
        FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();

        ctx.put("activities", activityDetailsList);

        engine.render(ctx, "templates/index.ftl", res -> {
            if (res.succeeded()) {
                ctx.response().end(res.result());
            } else {
                ctx.fail(res.cause());
            }
        });
    }

    @Override
    public void failure(Throwable throwable) {
        ctx.fail(throwable);
    }
}
