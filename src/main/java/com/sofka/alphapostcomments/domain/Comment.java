package com.sofka.alphapostcomments.domain;

import co.com.sofka.domain.generic.Entity;
import com.sofka.alphapostcomments.domain.values.Author;
import com.sofka.alphapostcomments.domain.values.CommentID;
import com.sofka.alphapostcomments.domain.values.Content;
import com.sofka.alphapostcomments.domain.values.Likes;

public class Comment extends Entity<CommentID> {

    private Author author;
    private Content content;
    private Likes likes;

    public Comment(CommentID entityId, Author author, Content content) {
        super(entityId);
        this.author = author;
        this.content = content;
        this.likes = new Likes(0);
    }

    public Author author() {
        return author;
    }

    public Content content() {
        return content;
    }

    public void increaseLikes(){
        this.likes =  new Likes(this.likes.value() + 1);
    }
}
