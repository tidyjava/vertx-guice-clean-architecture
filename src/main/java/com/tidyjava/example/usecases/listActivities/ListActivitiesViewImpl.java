package com.tidyjava.example.usecases.listActivities;

import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

public class ListActivitiesViewImpl implements ListActivitiesView {
    private final RoutingContext ctx;

    public ListActivitiesViewImpl(RoutingContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void generate(ListActivitiesViewModel viewModel) {
        final FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();

        ctx.put("activities", viewModel.getViewableActivityDetails());

        engine.render(ctx, "templates/index.ftl", res -> {
            if (res.succeeded()) {
                ctx.response().end(res.result());
            } else {
                ctx.fail(res.cause());
            }
        });
    }
}
