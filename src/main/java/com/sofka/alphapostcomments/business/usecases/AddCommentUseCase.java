package com.sofka.alphapostcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.alphapostcomments.business.gateways.DomainEventRepository;
import com.sofka.alphapostcomments.business.gateways.EventBus;
import com.sofka.alphapostcomments.domain.Post;
import com.sofka.alphapostcomments.domain.commands.AddComment;
import com.sofka.alphapostcomments.domain.values.PostID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class AddCommentUseCase {

    private final DomainEventRepository repository;

    private final EventBus bus;

    public AddCommentUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    public Flux<DomainEvent> apply(Mono<AddComment> addCommentMono) {
        return addCommentMono.flatMapMany(addCommentCommand -> repository.findById(addCommentCommand.getPostID())
                .collectList()
                    .flatMapIterable(eventsFromRepository -> {
                        Post post = Post.from(PostID.of(addCommentCommand.getPostID()), eventsFromRepository);
                        post.addComment(addCommentCommand.getCommentID(), addCommentCommand.getAuthor(), addCommentCommand.getContent());
                        log.info("Comment Added");
                        return post.getUncommittedChanges();
                    }).map(event -> {
                    bus.publish(event);
                    return event;
                    }).flatMap(event -> repository.saveEvent((DomainEvent) event))
                .doOnError(error -> log.error(String.valueOf(error))));
    }
}



