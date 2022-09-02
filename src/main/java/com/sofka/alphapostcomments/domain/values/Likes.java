package com.sofka.alphapostcomments.domain.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Likes implements ValueObject<Integer> {

    private final Integer value;

    public Likes(Integer value) {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public Integer value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Likes likes = (Likes) o;
        return Objects.equals(value, likes.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
