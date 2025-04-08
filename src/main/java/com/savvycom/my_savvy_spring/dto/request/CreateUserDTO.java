package com.savvycom.my_savvy_spring.dto.request;

import java.io.Serializable;

public class CreateUserDTO implements Serializable {
    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String email;
    private final String password;

    public CreateUserDTO(String firstName, String lastName, String userName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
