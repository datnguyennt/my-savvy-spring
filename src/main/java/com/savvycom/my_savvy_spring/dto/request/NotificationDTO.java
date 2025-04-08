package com.savvycom.my_savvy_spring.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private String title;
    private String body;
    private String token;  // Device token for individual device
    private String topic;  // Topic for broadcast to multiple devices
    private Map<String, String> data; // Additional data payload
}