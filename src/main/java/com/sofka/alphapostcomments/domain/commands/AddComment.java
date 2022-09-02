package com.sofka.alphapostcomments.domain.commands;

import co.com.sofka.domain.generic.Command;
import com.sofka.alphapostcomments.domain.values.Author;
import com.sofka.alphapostcomments.domain.values.CommentID;
import com.sofka.alphapostcomments.domain.values.Content;

public class AddComment extends Command {

    private String postID;
    private String commentID;
    private String author;
    private String content;


    public AddComment() {

    }

    public AddComment(String postID, String commentID, String author, String content) {
        this.postID = postID;
        this.commentID = commentID;
        this.author = author;
        this.content = content;
    }



    public String getPostID() {
        return postID;
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
