package com.tidyjava.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tidyjava.example.usecases.listActivities.ListActivitiesInputBoundary;
import com.tidyjava.example.usecases.listActivities.ListActivitiesPresenter;
import com.tidyjava.example.usecases.listActivities.ListActivitiesUseCase;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        initDatabase();

        Injector injector = Guice.createInjector(new ActivityModule(vertx, config()));
        ListActivitiesInputBoundary listActivitiesUseCase = injector.getInstance(ListActivitiesUseCase.class);

        Router.router(vertx).get("/").handler(ctx -> {
            ListActivitiesPresenter presenter = new ListActivitiesPresenter(ctx);
            listActivitiesUseCase.listActivities(presenter);
        });

        vertx.createHttpServer()
                .requestHandler(Router.router(vertx)::accept)
                .listen(8080);
    }

    private void initDatabase() {
        JDBCClient client = JDBCClient.createShared(vertx, config());

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
