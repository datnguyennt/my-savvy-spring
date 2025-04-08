package com.savvycom.my_savvy_spring.controller;

import com.google.firebase.messaging.FirebaseMessagingException;
import com.savvycom.my_savvy_spring.common.ApiResponse;
import com.savvycom.my_savvy_spring.dto.request.NotificationDTO;
import com.savvycom.my_savvy_spring.dto.request.SubscribeRequestDTO;
import com.savvycom.my_savvy_spring.exception.FirebaseNotificationException;
import com.savvycom.my_savvy_spring.service.FirebaseNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final FirebaseNotificationService notificationService;

    @Autowired
    public NotificationController(FirebaseNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send")
    public ResponseEntity<ApiResponse> sendNotification(@RequestBody NotificationDTO notificationDTO) {
        try {
            String response = notificationService.sendNotification(notificationDTO);
            return ResponseEntity.ok(new ApiResponse(true, response));
        } catch (FirebaseMessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to send notification: " + e.getMessage()));
        } catch (FirebaseNotificationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to send notification: " + e.getMessage()));
        }
    }

    @PostMapping("/send-multicast")
    public ResponseEntity<ApiResponse> sendMulticastNotification(
            @RequestBody NotificationDTO notificationDTO,
            @RequestParam String[] tokens) {
        try {
            String response = notificationService.sendMulticastNotification(notificationDTO, tokens);
            return ResponseEntity.ok(new ApiResponse(true, response));
        } catch (FirebaseMessagingException | FirebaseNotificationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to send multicast notification: " + e.getMessage()));
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<ApiResponse> subscribeToTopic(@RequestBody SubscribeRequestDTO subscribeRequestDTO) {
        try {
            String response = notificationService.subscribeToTopic(
                    subscribeRequestDTO.getToken(),
                    subscribeRequestDTO.getTopic());
            return ResponseEntity.ok(new ApiResponse(true, response));
        } catch (FirebaseMessagingException | FirebaseNotificationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to subscribe to topic: " + e.getMessage()));
        } catch (Exception e) {
            // Handle any other exceptions that may occur
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse(false, "Failed to subscribe to topic: " + e.getMessage()));
        }
    }
}