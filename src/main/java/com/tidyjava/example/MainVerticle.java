package com.tidyjava.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tidyjava.example.usecases.listActivities.ListActivitiesInputBoundary;
import com.tidyjava.example.usecases.listActivities.ListActivitiesModule;
import com.tidyjava.example.usecases.listActivities.ListActivitiesUseCase;
import com.tidyjava.example.usecases.listActivities.ListActivitiesPresenter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.templ.FreeMarkerTemplateEngine;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        initDatabase();

        Injector injector = Guice.createInjector(new ListActivitiesModule(vertx));
        ListActivitiesInputBoundary listActivitiesUseCase = injector.getInstance(ListActivitiesUseCase.class);

        final Router router = Router.router(vertx);

        router.get().handler(ctx -> {
            ListActivitiesPresenter presenter = new ListActivitiesPresenter(ctx);
            listActivitiesUseCase.listActivities(presenter);
        }).failureHandler(ctx -> {
            FreeMarkerTemplateEngine engine = FreeMarkerTemplateEngine.create();

            engine.render(ctx, "templates/error.ftl", res -> {
                if (res.succeeded()) {
                    ctx.response().end(res.result());
                } else {
                    System.out.println("DUPA");
                    ctx.fail(res.cause());
                }
            });
        });

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(8080);
    }

    private void initDatabase() {
        JsonObject config = new JsonObject()
                .put("url", "jdbc:h2:mem:test")
                .put("driver_class", "org.h2.Driver");

        JDBCClient client = JDBCClient.createShared(vertx, config);

        client.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection connection = res.result();
                connection.update("CREATE TABLE ACTIVITIES(ID VARCHAR(255) PRIMARY KEY, NAME VARCHAR(255));", upres -> {
                    if (upres.succeeded()) {
                        System.out.println("Success 1!");
                    } else {
                        System.out.println("Almost 1!");
                    }
                });
                connection.update("INSERT INTO ACTIVITIES(id, name) VALUES ('id1', 'name1');", upres -> {
                    if (upres.succeeded()) {
                        System.out.println("Success 2!");
                    } else {
                        System.out.println("Almost 2!");
                    }
                });
            } else {
                System.out.println("Holy Moly!");
            }
        });
    }
}
