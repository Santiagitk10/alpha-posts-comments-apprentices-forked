package com.sofka.alphapostcomments.application.adapters.bus;

import co.com.sofka.domain.generic.DomainEvent;
import com.google.gson.Gson;
import com.sofka.alphapostcomments.application.config.RabbitConfig;
import com.sofka.alphapostcomments.business.gateways.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RabbitMqEventBus implements EventBus {

    private final RabbitTemplate rabbitTemplate;
    private final Gson gson = new Gson();

    public RabbitMqEventBus(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(DomainEvent event) {
        log.info("Publishing To Rabbit");
        var notification = new Notification(
                event.getClass().getTypeName(),
                gson.toJson(event)
        );
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE, RabbitConfig.GENERAL_ROUTING_KEY, notification.serialize().getBytes()
        );
    }

    @Override
    public void publishError(Throwable errorEvent) {
        log.info("Publishing Error");
        var event = new ErrorEvent(errorEvent.getClass().getTypeName(), errorEvent.getMessage());
        var notification = new Notification(
                event.getClass().getTypeName(),
                gson.toJson(event)
        );
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE, RabbitConfig.GENERAL_ROUTING_KEY, notification.serialize().getBytes());
    }

}
