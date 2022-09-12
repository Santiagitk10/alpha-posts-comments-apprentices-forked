package com.sofka.alphapostcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.alphapostcomments.business.gateways.DomainEventRepository;
import com.sofka.alphapostcomments.business.gateways.EventBus;
import com.sofka.alphapostcomments.domain.Comment;
import com.sofka.alphapostcomments.domain.Post;
import com.sofka.alphapostcomments.domain.PostChange;
import com.sofka.alphapostcomments.domain.commands.AddComment;
import com.sofka.alphapostcomments.domain.commands.IncreaseLikes;
import com.sofka.alphapostcomments.domain.events.CommentAdded;
import com.sofka.alphapostcomments.domain.events.LikesIncreased;
import com.sofka.alphapostcomments.domain.events.PostCreated;
import com.sofka.alphapostcomments.domain.values.Author;
import com.sofka.alphapostcomments.domain.values.CommentID;
import com.sofka.alphapostcomments.domain.values.Content;
import net.bytebuddy.dynamic.DynamicType;
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
class IncreaseLikesUseCaseTest {

    @Mock
    private EventBus eventBusMock;

    @Mock
    private DomainEventRepository repositoryMock;

    @InjectMocks
    private IncreaseLikesUseCase useCaseMock;



    @Test
    @DisplayName("IncreaseLikesUseCaseTest. Should Increase the Likes in a Comment")
    void increaseLikesUseCaseTest(){
        //Arrange
        IncreaseLikes increaseLikesCommand = new IncreaseLikes(
            "1",
                "1"
        );


        LikesIncreased likesIncreasedEvent = new LikesIncreased(
                "1"
        );

        PostCreated postCreated = new PostCreated(
                "First Post",
                "Santiago Sierra"
        );

        CommentAdded commentAdded = new CommentAdded(
                "1",
                "Rafa Nadal",
                "He won the grandslam"
        );


        BDDMockito
                .when(this.repositoryMock.findById(ArgumentMatchers.anyString()))
                .thenReturn(Flux.just(postCreated, commentAdded));


        BDDMockito
                .when(this.repositoryMock.saveEvent(ArgumentMatchers.any(DomainEvent.class)))
                .thenReturn(Mono.just(likesIncreasedEvent));

        //Act
        Mono<List<DomainEvent>> savedEvent = this.useCaseMock.apply(Mono.just(increaseLikesCommand))
                .collectList();

        //Assert
        StepVerifier.create(savedEvent)
                .expectNextMatches(events ->
                        events.size() == 1 && events.get(0) instanceof LikesIncreased)
                .verifyComplete();

        BDDMockito.verify(this.eventBusMock, BDDMockito.times(1))
                .publish(ArgumentMatchers.any(DomainEvent.class));

        BDDMockito.verify(this.repositoryMock, BDDMockito.times(1))
                .saveEvent(ArgumentMatchers.any(DomainEvent.class));

        BDDMockito.verify(this.repositoryMock, BDDMockito.times(1))
                .findById(ArgumentMatchers.anyString());


    }


}