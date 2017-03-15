package com.tidyjava.example.gateways;

import com.tidyjava.example.callback.Callback;
import com.tidyjava.example.entities.Activity;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class ActivityGatewayImpl implements ActivityGateway {
    private final Vertx vertx;

    @Inject
    public ActivityGatewayImpl(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void findAll(Callback<List<Activity>> callback) {
        JsonObject config = new JsonObject()
                .put("url", "jdbc:h2:mem:test")
                .put("driver_class", "org.h2.Driver");

        JDBCClient client = JDBCClient.createShared(vertx, config);

        client.getConnection(res -> {
            if (res.succeeded()) {
                SQLConnection connection = res.result();
                connection.query("SELECT * FROM Activities;", res2 -> {
                    if (res2.succeeded()) {
                        List<JsonObject> rows = res2.result().getRows();

                        List<Activity> results = new ArrayList<>();
                        for (JsonObject row : rows) {
                            Activity activity = new Activity();
                            activity.setId(row.getString("ID"));
                            activity.setName(row.getString("NAME"));
                            results.add(activity);
                        }

                        callback.success(results);
                    } else {
                        callback.failure(res2.cause());
                    }
                });
                connection.close();
            } else {
                callback.failure(res.cause());
            }
        });
    }
}
