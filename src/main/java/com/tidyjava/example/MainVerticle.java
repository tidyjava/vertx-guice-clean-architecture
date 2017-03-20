package com.tidyjava.example;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.tidyjava.example.usecases.listActivities.ListActivitiesInputBoundary;
import com.tidyjava.example.usecases.listActivities.ListActivitiesUseCase;
import com.tidyjava.example.usecases.listActivities.ListActivitiesView;
import io.vertx.core.Future;
import io.vertx.rxjava.core.AbstractVerticle;
import io.vertx.rxjava.ext.jdbc.JDBCClient;
import io.vertx.rxjava.ext.sql.SQLConnection;
import io.vertx.rxjava.ext.web.Router;
import rx.Single;

public class MainVerticle extends AbstractVerticle {

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        initDatabase().subscribe(c -> {
            startFuture.complete();
        }, startFuture::fail);
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

    private Single<SQLConnection> initDatabase() {
        JDBCClient client = JDBCClient.createShared(vertx, config());
        return client.rxGetConnection()
            .flatMap(connection -> connection.rxUpdate("CREATE TABLE ACTIVITIES(ID VARCHAR(255) PRIMARY KEY, NAME VARCHAR(255));").map(r -> connection))
            .flatMap(connection -> connection.rxUpdate("INSERT INTO ACTIVITIES(id, name) VALUES ('id1', 'name1');").map(r -> connection));
    }
}
