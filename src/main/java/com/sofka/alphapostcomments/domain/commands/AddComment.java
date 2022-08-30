package com.sofka.alphapostcomments.domain.commands;

import co.com.sofka.domain.generic.Command;
import com.sofka.alphapostcomments.domain.values.Author;
import com.sofka.alphapostcomments.domain.values.CommentID;
import com.sofka.alphapostcomments.domain.values.Content;

public class AddComment extends Command {

    private CommentID commentID;
    private Author author;
    private Content content;

    public AddComment(CommentID commentID, Author author, Content content) {
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
