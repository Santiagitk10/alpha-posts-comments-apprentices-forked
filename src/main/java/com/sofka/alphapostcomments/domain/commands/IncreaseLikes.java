package com.sofka.alphapostcomments.domain.commands;

import co.com.sofka.domain.generic.Command;

public class IncreaseLikes extends Command {

    private String postID;
    private String commentID;

    public IncreaseLikes() {
    }

    public IncreaseLikes(String postID, String commentID) {
        this.postID = postID;
        this.commentID = commentID;
    }

    public String getPostID() {
        return postID;
    }

    public String getCommentID() {
        return commentID;
    }

}
