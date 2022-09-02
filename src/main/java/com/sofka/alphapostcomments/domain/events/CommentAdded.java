package com.sofka.alphapostcomments.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.alphapostcomments.domain.values.Author;
import com.sofka.alphapostcomments.domain.values.CommentID;
import com.sofka.alphapostcomments.domain.values.Content;

import java.util.UUID;

public class CommentAdded extends DomainEvent {

    //TODO Change to string

    private String commentID;
    private String author;
    private String content;

    public CommentAdded(String commentID, String author, String content) {
        super("sofka.alphapostcomments.domain.CommentAdded");
        this.commentID = commentID;
        this.author = author;
        this.content = content;
    }


    public CommentAdded() {
        super("sofka.alphapostcomments.domain.CommentAdded");
    }


    public String getCommentID() {
        return commentID;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
