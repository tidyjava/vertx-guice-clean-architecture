package com.tidyjava.example.usecases.listActivities;

import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

import java.util.List;

public class ListActivitiesViewImpl implements ListActivitiesView {
    private final RoutingContext ctx;

    public ListActivitiesViewImpl(RoutingContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void generate(List<ActivityDetails> responseModel) {
        FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();

        ctx.put("activities", responseModel);

        engine.render(ctx, "templates/index.ftl", res -> {
            if (res.succeeded()) {
                ctx.response().end(res.result());
            } else {
                ctx.fail(res.cause());
            }
        });
    }
}
