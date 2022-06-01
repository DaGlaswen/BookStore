package ru.learnup.bookStore.service.messagesService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public record MessageSender(String topicName, JmsTemplate jmsTemplate) {

    public MessageSender(
            @Value("${topic.name}") String topicName,
            JmsTemplate jmsTemplate) {
        this.topicName = topicName;
        this.jmsTemplate = jmsTemplate;
    }

    public void sendMessage(String message) {
        try {
            jmsTemplate.convertAndSend(topicName, message);
        } catch (Exception e) {
            log.error("Received an exception when sending a message: ", e);
        }
    }

}