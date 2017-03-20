package com.tidyjava.example.gateways;

import com.tidyjava.example.entities.Activity;
import io.vertx.core.json.JsonObject;
import io.vertx.rxjava.ext.jdbc.JDBCClient;
import rx.Single;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class ActivityGatewayImpl implements ActivityGateway {
    private JDBCClient jdbcClient;

    @Inject
    public ActivityGatewayImpl(JDBCClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public Single<List<Activity>> findAll() {
        return jdbcClient.rxGetConnection()
            .flatMap(connection -> connection.rxQuery("SELECT * FROM Activities;"))
            .map(result -> result
                .getRows()
                .stream()
                .map(this::toActivity)
                .collect(Collectors.toList()));
    }

    private Activity toActivity(JsonObject row) {
        Activity activity = new Activity();
        activity.setId(row.getString("ID"));
        activity.setName(row.getString("NAME"));
        return activity;
    }
}
