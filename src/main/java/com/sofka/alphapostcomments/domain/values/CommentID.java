package com.sofka.alphapostcomments.domain.values;

import co.com.sofka.domain.generic.Identity;

public class CommentID extends Identity {

    public CommentID() {

    }

    private CommentID(String id){
        super(id);
    }

    public static CommentID of(String id){
        return new CommentID(id);
    }

}
