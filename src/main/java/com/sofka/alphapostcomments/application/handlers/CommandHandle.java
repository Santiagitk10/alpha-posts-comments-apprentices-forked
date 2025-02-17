package com.sofka.alphapostcomments.application.handlers;

import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.alphapostcomments.business.usecases.AddCommentUseCase;
import com.sofka.alphapostcomments.business.usecases.CreatePostUseCase;
import com.sofka.alphapostcomments.business.usecases.IncreaseLikesUseCase;
import com.sofka.alphapostcomments.domain.commands.AddComment;
import com.sofka.alphapostcomments.domain.commands.CreatePost;
import com.sofka.alphapostcomments.domain.commands.IncreaseLikes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CommandHandle {

    @Bean
    public RouterFunction<ServerResponse> createPost(CreatePostUseCase useCase){

        return route(
                POST("/create/post").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.bodyToMono(CreatePost.class)), DomainEvent.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> addComment(AddCommentUseCase useCase){

        return route(
                POST("/add/comment").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.bodyToMono(AddComment.class)), DomainEvent.class))
        );
    }

    @Bean
    public RouterFunction<ServerResponse> increaseLikes(IncreaseLikesUseCase useCase){

        return route(
                POST("/increase/likes").and(accept(MediaType.APPLICATION_JSON)),
                request -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(useCase.apply(request.bodyToMono(IncreaseLikes.class)), DomainEvent.class))
        );
    }

}
