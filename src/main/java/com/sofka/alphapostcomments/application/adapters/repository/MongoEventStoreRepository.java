package com.sofka.alphapostcomments.application.adapters.repository;

import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import com.sofka.alphapostcomments.application.generic.models.StoredEvent;
import com.sofka.alphapostcomments.business.gateways.DomainEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Date;

@Slf4j
@Repository
public class MongoEventStoreRepository implements DomainEventRepository {

    private final ReactiveMongoTemplate template;

    private final Gson gson = new Gson();

    public MongoEventStoreRepository(ReactiveMongoTemplate template) {
        this.template = template;
    }

    @Override
    public Flux<DomainEvent> findById(String aggregateId) {
        var query = new Query(Criteria.where("aggregateRootId").is(aggregateId));
        return template.find(query, DocumentEventStored.class)
                .sort(Comparator.comparing(event -> event.getStoredEvent().getOccurredOn()))
                .map(storeEvent -> {
                    try {
                        return (DomainEvent) gson.fromJson(storeEvent.getStoredEvent().getEventBody(), Class.forName(storeEvent.getStoredEvent().getTypeName()));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        throw new IllegalStateException("couldnt find domain event");
                    }
                });
    }

    @Override
    public Mono<DomainEvent> saveEvent(DomainEvent event) {
        log.info("Saving Event");
        DocumentEventStored eventStored = new DocumentEventStored();
        eventStored.setAggregateRootId(event.aggregateRootId());
        eventStored.setStoredEvent(new StoredEvent(gson.toJson(event), new Date(), event.getClass().getCanonicalName()));
        return template.save(eventStored)
                .map(storeEvent -> {
                    try {
                        return (DomainEvent) gson.fromJson(storeEvent.getStoredEvent().getEventBody(), Class.forName(storeEvent.getStoredEvent().getTypeName()));
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                        throw new IllegalStateException("couldnt find domain event");
                    }
                })
                .doOnError(error -> log.error(String.valueOf(error)));
    }

}
