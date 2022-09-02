package com.sofka.alphapostcomments.domain.commands;

import co.com.sofka.domain.generic.Command;
import com.sofka.alphapostcomments.domain.values.Author;
import com.sofka.alphapostcomments.domain.values.PostID;
import com.sofka.alphapostcomments.domain.values.Title;

public class CreatePost extends Command {

    private String postID;
    private String title;
    private String author;

    public CreatePost(String postID, String title, String author) {
        this.postID = postID;
        this.title = title;
        this.author = author;
    }

    public CreatePost() {
    }

    public String getPostID() {
        return postID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }
}
