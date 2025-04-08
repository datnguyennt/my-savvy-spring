package com.savvycom.my_savvy_spring.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequestDTO {

    @NotBlank(message = "Student code is required")
    @Size(min = 2, max = 20, message = "Student code must be between 2 and 20 characters")
    private String studentCode;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be between 2 and 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    private String phone;
    private String address;
    private LocalDate dateOfBirth;

    @NotBlank(message = "Major is required")
    private String major;

    @NotNull(message = "Enrollment year is required")
    @Min(value = 2000, message = "Enrollment year must be at least 2000")
    @Max(value = 2100, message = "Enrollment year must be less than 2100")
    private Integer enrollmentYear;

    @DecimalMin(value = "0.0", message = "GPA must be at least 0.0")
    @DecimalMax(value = "4.0", message = "GPA must be at most 4.0")
    private Double gpa;
}