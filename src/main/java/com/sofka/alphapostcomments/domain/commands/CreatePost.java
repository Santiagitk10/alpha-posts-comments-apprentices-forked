package com.sofka.alphapostcomments.domain.commands;

import co.com.sofka.domain.generic.Command;
import com.sofka.alphapostcomments.domain.values.Author;
import com.sofka.alphapostcomments.domain.values.PostID;
import com.sofka.alphapostcomments.domain.values.Title;

public class CreatePost extends Command {

    private PostID postID;
    private Title title;
    private Author author;

    public CreatePost(PostID postID, Title title, Author author) {
        this.postID = postID;
        this.title = title;
        this.author = author;
    }

    public PostID getPostID() {
        return postID;
    }

    public Title getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }
}
