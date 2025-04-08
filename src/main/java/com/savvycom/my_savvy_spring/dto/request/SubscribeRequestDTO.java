package com.savvycom.my_savvy_spring.dto.request;

public class SubscribeRequestDTO {
    private String token;
    private String topic;

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}