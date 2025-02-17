package com.sofka.alphapostcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.alphapostcomments.business.gateways.DomainEventRepository;
import com.sofka.alphapostcomments.business.gateways.EventBus;
import com.sofka.alphapostcomments.domain.Post;
import com.sofka.alphapostcomments.domain.commands.CreatePost;
import com.sofka.alphapostcomments.domain.values.Author;
import com.sofka.alphapostcomments.domain.values.PostID;
import com.sofka.alphapostcomments.domain.values.Title;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CreatePostUseCase {

    private final DomainEventRepository repository;

    private final EventBus bus;

    public CreatePostUseCase(DomainEventRepository repository, EventBus bus) {
        this.repository = repository;
        this.bus = bus;
    }

    public Flux<DomainEvent> apply(Mono<CreatePost> createPostMono){
        log.info("Creating Post");
        return createPostMono.flatMapIterable(createPostCommand -> {
            Post post = new Post(PostID.of(createPostCommand.getPostID()),new Title(createPostCommand.getTitle()), new Author(createPostCommand.getAuthor()));
            log.info("Post Created");
            return post.getUncommittedChanges();
        }).flatMap(createPostEvent -> repository.saveEvent(createPostEvent).thenReturn(createPostEvent)).doOnNext(bus::publish)
                .doOnError(error -> log.error(String.valueOf(error)));

    }

}
