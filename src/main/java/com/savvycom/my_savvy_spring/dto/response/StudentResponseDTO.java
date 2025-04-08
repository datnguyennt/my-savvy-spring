package com.savvycom.my_savvy_spring.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
    private String id;
    private String studentCode;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private String major;
    private int enrollmentYear;
    private double gpa;
    private boolean active;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}