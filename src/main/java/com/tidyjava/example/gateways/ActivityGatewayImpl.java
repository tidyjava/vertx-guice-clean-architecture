package com.tidyjava.example.gateways;

import com.tidyjava.example.callback.Callback;
import com.tidyjava.example.entities.Activity;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;

import javax.inject.Inject;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ActivityGatewayImpl implements ActivityGateway {
    private JDBCClient jdbcClient;

    @Inject
    public ActivityGatewayImpl(JDBCClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public void findAll(Callback<List<Activity>> callback) {
        getConnection(connection -> connection.query("SELECT * FROM Activities;", asyncRs -> {
            if (asyncRs.succeeded()) {
                List<Activity> activities = asyncRs.result()
                        .getRows()
                        .stream()
                        .map(this::toActivity)
                        .collect(Collectors.toList());

                callback.success(activities);
            } else {
                callback.failure(asyncRs.cause());
            }
        }), callback::failure);
    }

    private void getConnection(Consumer<SQLConnection> sqlConnectionConsumer, Consumer<Throwable> onFailure) {
        jdbcClient.getConnection(asyncConn -> {
            if (asyncConn.succeeded()) {
                SQLConnection connection = asyncConn.result();
                sqlConnectionConsumer.accept(connection);
                connection.close();
            } else {
                onFailure.accept(asyncConn.cause());
            }
        });
    }

    private Activity toActivity(JsonObject row) {
        Activity activity = new Activity();
        activity.setId(row.getString("ID"));
        activity.setName(row.getString("NAME"));
        return activity;
    }
}
