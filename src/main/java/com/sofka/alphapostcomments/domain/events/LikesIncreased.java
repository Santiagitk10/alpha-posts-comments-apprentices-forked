package com.sofka.alphapostcomments.domain.events;

import co.com.sofka.domain.generic.DomainEvent;

public class LikesIncreased extends DomainEvent {

    private String commentID;

    public LikesIncreased(String commentID) {
        super("sofka.alphapostcomments.domain.LikesIncreased");
        this.commentID = commentID;
    }

    public LikesIncreased() {
        super("sofka.alphapostcomments.domain.LikesIncreased");
    }

    public String getCommentID() {
        return commentID;
    }

}
