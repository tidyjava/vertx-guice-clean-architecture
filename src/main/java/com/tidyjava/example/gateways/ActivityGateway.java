package com.tidyjava.example.gateways;

import com.tidyjava.example.entities.Activity;

import java.util.List;
import java.util.function.Consumer;

public interface ActivityGateway {
    void findAll(Consumer<List<Activity>> consumer);
}
