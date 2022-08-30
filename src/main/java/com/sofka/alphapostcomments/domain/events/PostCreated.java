package com.sofka.alphapostcomments.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.alphapostcomments.domain.values.Author;
import com.sofka.alphapostcomments.domain.values.Title;

public class PostCreated extends DomainEvent {

    private Title title;
    private Author author;

    public PostCreated(Title title, Author author) {
        super("sofka.alphapostcomments.domain.PostCreated");
        this.title = title;
        this.author = author;
    }

    public Title getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }
}
