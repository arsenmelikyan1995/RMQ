package com.example.common.service;

import com.example.common.model.RandomIntObject;

public interface RandomIntService {

    RandomIntObject save(RandomIntObject obj);

    RandomIntObject get(String id);

}
