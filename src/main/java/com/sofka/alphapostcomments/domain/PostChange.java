package com.sofka.alphapostcomments.domain;

import co.com.sofka.domain.generic.EventChange;
import com.sofka.alphapostcomments.domain.events.CommentAdded;
import com.sofka.alphapostcomments.domain.events.LikesIncreased;
import com.sofka.alphapostcomments.domain.events.PostCreated;
import com.sofka.alphapostcomments.domain.values.Author;
import com.sofka.alphapostcomments.domain.values.CommentID;
import com.sofka.alphapostcomments.domain.values.Content;
import com.sofka.alphapostcomments.domain.values.Title;

import java.util.ArrayList;

public class PostChange extends EventChange {

    public PostChange(Post post){

        apply((PostCreated event) -> {
            post.title = new Title(event.getTitle());
            post.author = new Author(event.getAuthor());
            post.comments = new ArrayList<>();
        } );


        apply((CommentAdded event) -> {
            post.comments.add(new Comment(
                    (CommentID.of(event.getCommentID())),
                    new Author(event.getAuthor()),
                    new Content(event.getContent()
            )));
        });

        apply((LikesIncreased event) -> {
            var comment = post.getCommentByID(CommentID.of(event.getCommentID())).orElseThrow(() -> new IllegalArgumentException("Invalid ID to retrive Comment"));
            comment.increaseLikes();
        });

    }

}
