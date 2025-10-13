package com.listener.clientservicelistener.jms;
import com.listener.clientservicelistener.service.NotificationService;
import com.mycompany.shared.dto.ClientEventDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ClientEventListener {

    private final JmsTemplate jmsTemplate;
    private final NotificationService notificationService;

    @JmsListener(destination = "client.queue")
    public void receiveEvent(ClientEventDTO event) {
        log.info("Recibido evento de cliente: {}", event);
        notificationService.saveNotification(event);
        log.info("Notificación guardada en MongoDB para cliente {}", event.getClientId());
    }

    @CircuitBreaker(name = "clientListenerBreaker", fallbackMethod = "handleFailure")
    @JmsListener(destination = "client.queue", containerFactory = "jmsListenerContainerFactory")
    public void receiveClientEvent(ClientEventDTO event) {
        log.info("Received client event: {} for client ID: {}", event.getEventType(), event.getClientId());
        String replyMessage = "Cliente " + event.getClientId() + " processed successfully";
        jmsTemplate.convertAndSend("client.reply.queue", replyMessage);
        log.info("Respuesta enviada");
    }

    private void handleFailure(ClientEventDTO event, Throwable t) {
        log.error("Fallo al procesar evento {}. Error: {}", event, t.getMessage());
        jmsTemplate.convertAndSend("client.dlq", event);
        log.info("Evento enviado a DLQ");
    }
}
