package com.tidyjava.example.gateways;

import com.tidyjava.example.callback.Callback;
import com.tidyjava.example.entities.Activity;

import java.util.List;
import java.util.function.Consumer;

public interface ActivityGateway {
    void findAll(Callback<List<Activity>> callback);
}
