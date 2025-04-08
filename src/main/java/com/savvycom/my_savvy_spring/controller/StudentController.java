package com.savvycom.my_savvy_spring.controller;

import com.savvycom.my_savvy_spring.common.ApiResponse;
import com.savvycom.my_savvy_spring.dto.request.StudentRequestDTO;
import com.savvycom.my_savvy_spring.dto.response.StudentResponseDTO;
import com.savvycom.my_savvy_spring.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createStudent(@Valid @RequestBody StudentRequestDTO requestDTO) {
        StudentResponseDTO responseDTO = studentService.createStudent(requestDTO);
        ApiResponse response = new ApiResponse(
                true,
                "Student created successfully",
                responseDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        ApiResponse response = new ApiResponse(
                true,
                "Students retrieved successfully",
                students);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getStudentById(@PathVariable String id) {
        StudentResponseDTO student = studentService.getStudentById(id);
        ApiResponse response = new ApiResponse(
                true,
                "Student retrieved successfully",
                student);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/code/{studentCode}")
    public ResponseEntity<ApiResponse> getStudentByCode(@PathVariable String studentCode) {
        StudentResponseDTO student = studentService.getStudentByStudentCode(studentCode);
        ApiResponse response = new ApiResponse(
                true,
                "Student retrieved successfully",
                student);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchStudents(@RequestParam String name) {
        List<StudentResponseDTO> students = studentService.searchStudents(name);
        ApiResponse response = new ApiResponse(
                true,
                "Students searched successfully",
                students);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/major/{major}")
    public ResponseEntity<ApiResponse> getStudentsByMajor(@PathVariable String major) {
        List<StudentResponseDTO> students = studentService.getStudentsByMajor(major);
        ApiResponse response = new ApiResponse(
                true,
                "Students retrieved by major successfully",
                students);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudent(
            @PathVariable String id,
            @Valid @RequestBody StudentRequestDTO requestDTO) {
        StudentResponseDTO updatedStudent = studentService.updateStudent(id, requestDTO);
        ApiResponse response = new ApiResponse(
                true,
                "Student updated successfully",
                updatedStudent);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
        ApiResponse response = new ApiResponse(
                true,
                "Student deleted successfully",
                null);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse> deactivateStudent(@PathVariable String id) {
        StudentResponseDTO deactivatedStudent = studentService.deactivateStudent(id);
        ApiResponse response = new ApiResponse(
                true,
                "Student deactivated successfully",
                deactivatedStudent);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse> activateStudent(@PathVariable String id) {
        StudentResponseDTO activatedStudent = studentService.activateStudent(id);
        ApiResponse response = new ApiResponse(
                true,
                "Student activated successfully",
                activatedStudent);
        return ResponseEntity.ok(response);
    }
}