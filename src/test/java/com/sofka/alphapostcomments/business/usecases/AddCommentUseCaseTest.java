package com.sofka.alphapostcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.alphapostcomments.business.gateways.DomainEventRepository;
import com.sofka.alphapostcomments.business.gateways.EventBus;
import com.sofka.alphapostcomments.domain.commands.AddComment;
import com.sofka.alphapostcomments.domain.commands.CreatePost;
import com.sofka.alphapostcomments.domain.events.CommentAdded;
import com.sofka.alphapostcomments.domain.events.PostCreated;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AddCommentUseCaseTest {


    @Mock
    private EventBus eventBusMock;

    @Mock
    private DomainEventRepository repositoryMock;

    @InjectMocks
    private AddCommentUseCase useCaseMock;

    @Test
    @DisplayName("AddCommentUseCaseTest. Should create a new Comment inside a post")
    void addCommentUseCaseTest(){
        //Arrange
        AddComment addCommentCommand = new AddComment(
            "1",
                "1",
                "Rafa Nadal",
                "First Comment to First Post"
        );

        CommentAdded commentAddedEvent = new CommentAdded(
                addCommentCommand.getCommentID(),
                addCommentCommand.getAuthor(),
                addCommentCommand.getContent()
        );


        BDDMockito
                .when(this.repositoryMock.findById(ArgumentMatchers.anyString()))
                .thenReturn(Flux.just(new PostCreated(
                        "First Post",
                        "Santiago Sierra"
                )));

        BDDMockito
                .when(this.repositoryMock.saveEvent(ArgumentMatchers.any(DomainEvent.class)))
                .thenReturn(Mono.just(commentAddedEvent));

        //Act
        Mono<List<DomainEvent>> savedEvent = this.useCaseMock.apply(Mono.just(addCommentCommand))
                .collectList();

        //Assert
        StepVerifier.create(savedEvent)
                .expectNextMatches(events ->
                        events.size() == 1 && events.get(0) instanceof CommentAdded)
                .verifyComplete();

        BDDMockito.verify(this.eventBusMock, BDDMockito.times(1))
                .publish(ArgumentMatchers.any(DomainEvent.class));

        BDDMockito.verify(this.repositoryMock, BDDMockito.times(1))
                .saveEvent(ArgumentMatchers.any(DomainEvent.class));

        BDDMockito.verify(this.repositoryMock, BDDMockito.times(1))
                .findById(ArgumentMatchers.anyString());

    }

}