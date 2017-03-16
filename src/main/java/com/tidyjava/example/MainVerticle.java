package com.tidyjava.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tidyjava.example.usecases.listActivities.ListActivitiesInputBoundary;
import com.tidyjava.example.usecases.listActivities.ListActivitiesUseCase;
import com.tidyjava.example.usecases.listActivities.ListActivitiesView;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        initDatabase();

        Router router = Router.router(vertx);
        Injector injector = Guice.createInjector(new ActivityModule(vertx, config()));

        ListActivitiesInputBoundary listActivitiesUseCase = injector.getInstance(ListActivitiesUseCase.class);
        router.get().handler(ctx -> {
            ListActivitiesView view = new ListActivitiesView(ctx);
            listActivitiesUseCase.listActivities(view);
        });

        vertx.createHttpServer()
                .requestHandler(router::accept)
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
