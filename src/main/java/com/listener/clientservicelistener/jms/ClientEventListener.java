package com.listener.clientservicelistener.jms;
import com.listener.clientservicelistener.service.NotificationService;
import com.mycompany.shared.dto.ClientEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientEventListener {

    private final JmsTemplate jmsTemplate;
    private final NotificationService notificationService;

    @JmsListener(destination = "client.queue", containerFactory = "jmsListenerContainerFactory")
    public void receiveClientEvent(ClientEventDTO event) {
        log.info("Recibido evento: {} para cliente {}", event.getEventType(), event.getClientId());
        notificationService.saveNotification(event);
        log.info("Notificación guardada en MongoDB");
        jmsTemplate.convertAndSend("client.reply.queue", "Cliente " + event.getClientId() + " procesado con éxito");
    }


}
