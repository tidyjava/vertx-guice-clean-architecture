package com.tidyjava.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tidyjava.example.usecases.listActivities.ListActivitiesController;
import com.tidyjava.example.usecases.listActivities.ListActivitiesModule;
import com.tidyjava.example.usecases.listActivities.ListActivitiesView;
import com.tidyjava.example.usecases.listActivities.ListActivitiesViewImpl;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {
        initDatabase();

        Injector injector = Guice.createInjector(new ListActivitiesModule(vertx));
        ListActivitiesController listActivitiesController = injector.getInstance(ListActivitiesController.class);

        final Router router = Router.router(vertx);

        router.get().handler(ctx -> {
            ListActivitiesView listActivitiesView = new ListActivitiesViewImpl(ctx);
            listActivitiesController.handle(listActivitiesView);
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
