package com.listener.clientservicelistener.jms;
import com.listener.clientservicelistener.entity.Notification;
import com.listener.clientservicelistener.repository.NotificationRepository;
import com.mycompany.shared.dto.ClientEventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientEventListener {

    private final NotificationRepository notificationRepository;

    @JmsListener(destination = "client.queue", containerFactory = "jmsListenerContainerFactory")
    public void receiveMessage(ClientEventDTO event) {
        log.info("Received client event: {} for client ID: {}. Message: {}",
                event.getEventType(), event.getClientId(), event.getMessage());

        // Guardar cualquier tipo de evento
        notificationRepository.save(Notification.builder()
                .clientId(event.getClientId())
                .eventType(event.getEventType())
                .clientEmail(event.getClientEmail())
                .message(event.getMessage())
                .receiveAt(LocalDateTime.now())
                .build());
    }
//    public void receiveClientEvent(ClientEventDTO event) {
//        log.info("Recibido evento: {} para cliente {}", event.getEventType(), event.getClientId());
//        notificationService.saveNotification(event);
//        log.info("Notificación guardada en MongoDB");
//        jmsTemplate.convertAndSend("client.reply.queue", "Cliente " + event.getClientId() + " procesado con éxito");
//    }


}
