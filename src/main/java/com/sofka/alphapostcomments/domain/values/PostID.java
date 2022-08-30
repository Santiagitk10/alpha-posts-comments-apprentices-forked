package com.sofka.alphapostcomments.domain.values;

import co.com.sofka.domain.generic.Identity;

public class PostID extends Identity {

    public PostID() {

    }

    private PostID(String id){
        super(id);
    }

    public static PostID of(String id){
        return new PostID(id);
    }

}
