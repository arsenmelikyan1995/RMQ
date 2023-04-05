package com.example.common.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RandomIntObject implements Serializable {

    private String id = UUID.randomUUID().toString();

    private List<Integer> randomInt;

    public RandomIntObject() {
    }

    public RandomIntObject(List<Integer> randomInt) {
        this.randomInt = randomInt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Integer> getRandomInt() {
        return randomInt;
    }

    public void setRandomInt(List<Integer> randomInt) {
        this.randomInt = randomInt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomIntObject that = (RandomIntObject) o;
        return id.equals(that.id) &&
                Objects.equals(randomInt, that.randomInt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, randomInt);
    }
}
