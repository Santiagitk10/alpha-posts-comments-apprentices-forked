package com.sofka.alphapostcomments.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.alphapostcomments.domain.values.Author;
import com.sofka.alphapostcomments.domain.values.Title;

public class PostCreated extends DomainEvent {

    private String title;
    private String author;



    public PostCreated(String title, String author) {
        super("sofka.alphapostcomments.domain.PostCreated");
        this.title = title;
        this.author = author;
    }

    public PostCreated(String type) {
        super("sofka.alphapostcomments.domain.PostCreated");
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
