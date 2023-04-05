package com.example.common.service.impl;

import com.example.common.exception.RandomIntObjectNotFoundException;
import com.example.common.model.RandomIntObject;
import com.example.common.repository.RandomIntRepository;
import com.example.common.service.RandomIntService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomIntServiceImplTest {

    @Autowired
    private RandomIntService service;

    @MockBean
    private RandomIntRepository repository;

    @Test
    public void onlyRandomIntServiceIsLoaded(){
        assertThat(service).isNotNull();
    }

    @Test
    public void whenSaveRandomIntObject_thenReturnRandomIntObject() {
        RandomIntObject obj = new RandomIntObject();
        doReturn(obj)
                .when(repository)
                .save(obj);
        Assert.assertEquals(obj,service.save(obj));
        verify(repository, times(1)).save(obj);
    }

    @Test
    public void whenValidId_thenRandomIntObjectShouldBeFound() {
        RandomIntObject obj = new RandomIntObject();
        doReturn(Optional.of(obj))
                .when(repository)
                .findById(obj.getId());
        Assert.assertEquals(obj,Optional.of(service.get(obj.getId())).get());
        verify(repository, times(1)).findById(obj.getId());
    }

    @Test(expected = RandomIntObjectNotFoundException.class)
    public void whenNotValidId_thenRandomIntObjectShouldThrowException() {
        service.get("Object not found");
        verify(repository, times(1)).findById("Object not found");
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenNullId_thenRandomIntObjectShouldThrowException() {
        doThrow(IllegalArgumentException.class)
                .when(repository)
                .findById(null);
        service.get(null);
        verify(repository, times(1)).findById(null);
    }}
