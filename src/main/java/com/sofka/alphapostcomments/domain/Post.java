package com.sofka.alphapostcomments.domain;

import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.alphapostcomments.domain.events.CommentAdded;
import com.sofka.alphapostcomments.domain.events.LikesIncreased;
import com.sofka.alphapostcomments.domain.events.PostCreated;
import com.sofka.alphapostcomments.domain.values.*;

import java.lang.module.Configuration;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Post extends AggregateEvent<PostID> {


    protected Title title;
    protected Author author;
    protected List<Comment> comments;

    public Post(PostID postID, Title title, Author author) {
        super(postID);
        appendChange(new PostCreated(title.value(), author.value()));
    }

    private Post(PostID postID){
        super(postID);
        subscribe(new PostChange(this));
    }

    public static Post from(PostID postID, List<DomainEvent> events){
        var post = new Post(postID);
        events.forEach(post::applyEvent);
        return post;
    }

    //TODO Validate if parameters in the method to append the event are OK as strings
    /*public void addComment(CommentID commentID, Author author, Content content){
        Objects.requireNonNull(commentID);
        Objects.requireNonNull(author);
        Objects.requireNonNull(content);
        appendChange(new CommentAdded(commentID.value(), author.value(), content.value() )).apply();
    }*/

    public void addComment(String commentID, String author, String content){
        Objects.requireNonNull(commentID);
        Objects.requireNonNull(author);
        Objects.requireNonNull(content);
        appendChange(new CommentAdded(commentID, author, content)).apply();
    }


    public void increaseLikes(String commentID){
        Objects.requireNonNull(commentID);
        appendChange(new LikesIncreased(commentID)).apply();
    }

    public Optional<Comment> getCommentByID(CommentID commentID){
        return comments.stream().filter((comment -> comment.identity().equals(commentID))).findFirst();
    }

    public Title title() {
        return title;
    }

    public Author author() {
        return author;
    }

    public List<Comment> comments() {
        return comments;
    }
}
