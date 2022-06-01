package ru.learnup.bookStore.service.messagesService;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.IOException;

@Service
@Slf4j
public class MessageConsumer implements MessageListener {

    @JmsListener(destination = "${topic.name}")
    @Override
    public void onMessage(Message message) {
        try{
            ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
            String msg = textMessage.getText();
            log.info("Received a message: " + msg);
        } catch(Exception e) {
            log.error("Received an exception : " + e);
        }
    }

}