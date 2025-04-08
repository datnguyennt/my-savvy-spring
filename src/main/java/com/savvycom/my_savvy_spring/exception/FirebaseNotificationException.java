package com.savvycom.my_savvy_spring.exception;

import com.google.firebase.messaging.FirebaseMessagingException;

/**
 * Custom exception for Firebase notification errors
 */
public class FirebaseNotificationException extends RuntimeException {
    
    private final String errorCode;
    
    public FirebaseNotificationException(String message) {
        super(message);
        this.errorCode = "UNKNOWN_ERROR";
    }
    
    public FirebaseNotificationException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "UNKNOWN_ERROR";
    }
    
    public FirebaseNotificationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
    
    public FirebaseNotificationException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    /**
     * Create exception from Firebase's exception
     */
    public static FirebaseNotificationException fromFirebaseException(FirebaseMessagingException e) {
        return new FirebaseNotificationException(
            e.getMessage(),
            e.getMessagingErrorCode() != null ? e.getMessagingErrorCode().name() : "UNKNOWN_ERROR",
            e
        );
    }
}