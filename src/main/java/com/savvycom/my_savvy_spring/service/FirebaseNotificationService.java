package com.savvycom.my_savvy_spring.service;

import com.google.firebase.messaging.*;
import com.savvycom.my_savvy_spring.dto.request.NotificationDTO;
import com.savvycom.my_savvy_spring.exception.FirebaseNotificationException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.Arrays;
import java.util.List;

@Service
public class FirebaseNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseNotificationService.class);

    public String sendNotification(NotificationDTO notificationDTO) throws FirebaseMessagingException {
        // Create notification message
        Message message = buildMessage(notificationDTO);

        try {
            // Send a message
            String response = FirebaseMessaging.getInstance().sendAsync(message).get();
            return "Successfully sent notification: " + response;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Notification sending was interrupted", e);
            throw new FirebaseNotificationException("Notification sending was interrupted", e);
        } catch (ExecutionException e) {
            logger.error("Failed to send notification", e);
            throw new FirebaseNotificationException("Failed to send notification", e);
        }
    }

    public String sendMulticastNotification(NotificationDTO notificationDTO, String[] tokens)
            throws FirebaseMessagingException {
        MulticastMessage message = buildMulticastMessage(notificationDTO, tokens);

        try {
            // Send messages to multiple devices
            BatchResponse response = FirebaseMessaging.getInstance().sendEachForMulticast(message);
            return String.format("Successfully sent %d notifications, %d failed",
                    response.getSuccessCount(), response.getFailureCount());
        } catch (FirebaseMessagingException e) {
            throw FirebaseNotificationException.fromFirebaseException(e);
        }
    }

    public String subscribeToTopic(String token, String topic) throws FirebaseMessagingException {
        try {
            TopicManagementResponse response = FirebaseMessaging.getInstance()
                    .subscribeToTopic(List.of(token), topic);

            return String.format("Successfully subscribed %d tokens, %d failed",
                    response.getSuccessCount(), response.getFailureCount());
        } catch (FirebaseMessagingException e) {
            throw FirebaseNotificationException.fromFirebaseException(e);
        }
    }

    public String sendNotificationWithCustomSoundAndImage(NotificationDTO notificationDTO, String sound,
            String imageUrl) throws FirebaseMessagingException {
        Message.Builder messageBuilder = Message.builder();

        // Add notification payload with custom sound and image
        if (notificationDTO.getTitle() != null && notificationDTO.getBody() != null) {
            Notification notification = Notification.builder()
                    .setTitle(notificationDTO.getTitle())
                    .setBody(notificationDTO.getBody())
                    .setImage(imageUrl)
                    .build();
            messageBuilder.setNotification(notification);
        }

        // Add data payload
        if (notificationDTO.getData() != null) {
            messageBuilder.putAllData(notificationDTO.getData());
        }

        // Add platform-specific configurations
        ApnsConfig apnsConfig = ApnsConfig.builder()
                .setAps(Aps.builder().setSound(sound).build())
                .build();
        messageBuilder.setApnsConfig(apnsConfig);

        AndroidConfig androidConfig = AndroidConfig.builder()
                .setNotification(AndroidNotification.builder().setSound(sound).build())
                .build();
        messageBuilder.setAndroidConfig(androidConfig);

        // Target can be either token (single device) or topic (multiple devices)
        if (notificationDTO.getToken() != null) {
            messageBuilder.setToken(notificationDTO.getToken());
        } else if (notificationDTO.getTopic() != null) {
            messageBuilder.setTopic(notificationDTO.getTopic());
        }

        try {
            // Send a message
            String response = FirebaseMessaging.getInstance().sendAsync(messageBuilder.build()).get();
            return "Successfully sent notification with custom sound and image: " + response;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("Notification sending was interrupted", e);
            throw new FirebaseNotificationException("Notification sending was interrupted", e);
        } catch (ExecutionException e) {
            logger.error("Failed to send notification", e);
            throw new FirebaseNotificationException("Failed to send notification", e);
        }
    }

    private Message buildMessage(NotificationDTO notificationDTO) {
        Message.Builder messageBuilder = Message.builder();

        // Add notification payload

        if (notificationDTO.getTitle() != null && notificationDTO.getBody() != null) {
            Notification notification = Notification.builder()
                    .setTitle(notificationDTO.getTitle())
                    .setBody(notificationDTO.getBody())
                    .build();
            messageBuilder.setNotification(notification);
        }

        // Add data payload
        if (notificationDTO.getData() != null) {
            messageBuilder.putAllData(notificationDTO.getData());
        }

        // Target can be either token (single device) or topic (multiple devices)
        if (notificationDTO.getToken() != null) {
            messageBuilder.setToken(notificationDTO.getToken());
        } else if (notificationDTO.getTopic() != null) {
            messageBuilder.setTopic(notificationDTO.getTopic());
        }

        return messageBuilder.build();
    }

    private MulticastMessage buildMulticastMessage(NotificationDTO notificationDTO, String[] tokens) {
        MulticastMessage.Builder messageBuilder = MulticastMessage.builder()
                .addAllTokens(Arrays.asList(tokens));

        // Add notification payload
        if (notificationDTO.getTitle() != null && notificationDTO.getBody() != null) {
            Notification notification = Notification.builder()
                    .setTitle(notificationDTO.getTitle())
                    .setBody(notificationDTO.getBody())
                    .build();
            messageBuilder.setNotification(notification);
        }

        // Add data payload
        if (notificationDTO.getData() != null) {
            messageBuilder.putAllData(notificationDTO.getData());
        }

        return messageBuilder.build();
    }
}