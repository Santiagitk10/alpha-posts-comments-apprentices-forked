package com.sofka.alphapostcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.alphapostcomments.business.gateways.DomainEventRepository;
import com.sofka.alphapostcomments.business.gateways.EventBus;
import com.sofka.alphapostcomments.domain.Post;
import com.sofka.alphapostcomments.domain.commands.AddComment;
import com.sofka.alphapostcomments.domain.commands.IncreaseLikes;
import com.sofka.alphapostcomments.domain.values.CommentID;
import com.sofka.alphapostcomments.domain.values.PostID;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class IncreaseLikesUseCase {

    private final DomainEventRepository repository;

    private final EventBus bus;

    public IncreaseLikesUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    public Flux<DomainEvent> apply(Mono<IncreaseLikes> increaseLikesMono) {
        return increaseLikesMono.flatMapMany(increaseLikesCommand -> repository.findById(increaseLikesCommand.getPostID())
                .collectList()
                .flatMapIterable(eventsFromRepository -> {
                    Post post = Post.from(PostID.of(increaseLikesCommand.getPostID()), eventsFromRepository);
                    post.increaseLikes(increaseLikesCommand.getCommentID());
                    return post.getUncommittedChanges();
                }).map(event -> {
                    bus.publish(event);
                    return event;
                }).flatMap(event -> repository.saveEvent((DomainEvent) event)));
    }
}
