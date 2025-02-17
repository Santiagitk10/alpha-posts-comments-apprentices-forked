package com.sofka.alphapostcomments.domain.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class Author implements ValueObject<String> {


    private final String value;


    public Author(String value) {
        this.value = Objects.requireNonNull(value);
        if(this.value.isBlank()){
            throw new IllegalArgumentException("The VO Author cannot be blank");
        }
        if(this.value.length() < 5){
            throw new IllegalArgumentException("The VO Author cannot have less than 5 letters");
        }
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(value, author.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
