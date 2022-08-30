package com.sofka.alphapostcomments.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.alphapostcomments.domain.values.Author;
import com.sofka.alphapostcomments.domain.values.CommentID;
import com.sofka.alphapostcomments.domain.values.Content;

public class CommentAdded extends DomainEvent {

    private CommentID commentID;
    private Author author;
    private Content content;

    public CommentAdded(CommentID commentID, Author author, Content content) {
        super("sofka.alphapostcomments.domain.CommentAdded");
        this.commentID = commentID;
        this.author = author;
        this.content = content;
    }

    public CommentID getCommentID() {
        return commentID;
    }

    public Author getAuthor() {
        return author;
    }

    public Content getContent() {
        return content;
    }
}
