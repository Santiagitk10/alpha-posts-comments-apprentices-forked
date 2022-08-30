package com.sofka.alphapostcomments.domain;

import co.com.sofka.domain.generic.Entity;
import com.sofka.alphapostcomments.domain.values.Author;
import com.sofka.alphapostcomments.domain.values.CommentID;
import com.sofka.alphapostcomments.domain.values.Content;

public class Comment extends Entity<CommentID> {

    private Author author;
    private Content content;

    public Comment(CommentID entityId, Author author, Content content) {
        super(entityId);
        this.author = author;
        this.content = content;
    }

    public Author author() {
        return author;
    }

    public Content content() {
        return content;
    }
}
