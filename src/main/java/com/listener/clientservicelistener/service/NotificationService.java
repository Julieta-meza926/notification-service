package com.listener.clientservicelistener.service;

import com.listener.clientservicelistener.entity.Notification;
import com.listener.clientservicelistener.repository.NotificationRepository;
import com.mycompany.shared.dto.ClientEventDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public void saveNotification (ClientEventDTO event){
        Notification notification = Notification.builder()
                .clientId(event.getClientId())
                .eventType(event.getEventType())
                .clientEmail(event.getClientEmail())
                .message(event.getMessage())
                .receiveAt(LocalDateTime.now())
                .build();

        notificationRepository.save(notification);
    }
}
