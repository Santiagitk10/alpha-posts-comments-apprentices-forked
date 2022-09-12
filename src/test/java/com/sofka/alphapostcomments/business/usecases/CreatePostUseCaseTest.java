package com.sofka.alphapostcomments.business.usecases;

import co.com.sofka.domain.generic.DomainEvent;
import com.sofka.alphapostcomments.business.gateways.DomainEventRepository;
import com.sofka.alphapostcomments.business.gateways.EventBus;
import com.sofka.alphapostcomments.domain.commands.CreatePost;
import com.sofka.alphapostcomments.domain.events.PostCreated;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CreatePostUseCaseTest {

    @Mock
    private EventBus eventBusMock;

    @Mock
    private DomainEventRepository repositoryMock;

    @InjectMocks
    private CreatePostUseCase useCaseMock;

    @Test
    @DisplayName("CreatePostUseCaseTest. Should Create a new Post")
    void createPostUseCaseTest(){

        //Arrange
        CreatePost createPostCommand = new CreatePost(
                "1",
                "First Post",
                "Santiago Sierra"
        );

        PostCreated postCreatedEvent = new PostCreated(
                createPostCommand.getTitle(),
                createPostCommand.getAuthor()
        );

        BDDMockito
                .when(this.repositoryMock.saveEvent(ArgumentMatchers.any(DomainEvent.class)))
                .thenReturn(Mono.just(postCreatedEvent));


        //Act
        Mono<List<DomainEvent>> savedEvent = this.useCaseMock.apply(Mono.just(createPostCommand))
                .collectList();

        //Assert
        StepVerifier.create(savedEvent)
                .expectNextMatches(events ->
                        events.size() == 1 && events.get(0) instanceof PostCreated)
                .verifyComplete();

        BDDMockito.verify(this.eventBusMock, BDDMockito.times(1))
                .publish(ArgumentMatchers.any(DomainEvent.class));

        BDDMockito.verify(this.repositoryMock, BDDMockito.times(1))
                .saveEvent(ArgumentMatchers.any(DomainEvent.class));

    }

}