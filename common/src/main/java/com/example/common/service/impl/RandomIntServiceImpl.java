package com.example.common.service.impl;

import com.example.common.exception.RandomIntObjectNotFoundException;
import com.example.common.model.RandomIntObject;
import com.example.common.repository.RandomIntRepository;
import com.example.common.service.RandomIntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RandomIntServiceImpl implements RandomIntService {

    @Autowired private RandomIntRepository repository;

    @Override public RandomIntObject save(RandomIntObject obj) {
        return repository.save(obj);
    }

    @Override public RandomIntObject get(String id) {
        return repository.findById(id).orElseThrow(
                () -> new RandomIntObjectNotFoundException(String.format("object not found by id %s.", id)));
    }
}
