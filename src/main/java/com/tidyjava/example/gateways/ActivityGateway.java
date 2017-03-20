package com.tidyjava.example.gateways;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import com.tidyjava.example.callback.Callback;
import com.tidyjava.example.entities.Activity;
import rx.Single;

import java.util.List;

public interface ActivityGateway {
    Single<List<Activity>> findAll();
}
