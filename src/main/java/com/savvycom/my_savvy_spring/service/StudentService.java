package com.savvycom.my_savvy_spring.service;

import com.savvycom.my_savvy_spring.dto.request.StudentRequestDTO;
import com.savvycom.my_savvy_spring.dto.response.StudentResponseDTO;
import com.savvycom.my_savvy_spring.model.Student;
import com.savvycom.my_savvy_spring.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO) {
        // Check if student with same code or email already exists
        if (studentRepository.existsByStudentCode(requestDTO.getStudentCode())) {
            throw new IllegalArgumentException("Student with code " + requestDTO.getStudentCode() + " already exists");
        }

        if (studentRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Student with email " + requestDTO.getEmail() + " already exists");
        }

        // Map DTO to entity
        Student student = new Student();
        mapDtoToEntity(requestDTO, student);

        // Save the student
        Student savedStudent = studentRepository.save(student);

        // Map entity to response DTO and return
        return mapEntityToResponseDto(savedStudent);
    }

    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::mapEntityToResponseDto)
                .collect(Collectors.toList());
    }

    public StudentResponseDTO getStudentById(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));
        return mapEntityToResponseDto(student);
    }

    public StudentResponseDTO getStudentByStudentCode(String studentCode) {
        Student student = studentRepository.findByStudentCode(studentCode)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with code: " + studentCode));
        return mapEntityToResponseDto(student);
    }

    public List<StudentResponseDTO> searchStudents(String name) {
        return studentRepository.findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(name, name).stream()
                .map(this::mapEntityToResponseDto)
                .collect(Collectors.toList());
    }

    public List<StudentResponseDTO> getStudentsByMajor(String major) {
        return studentRepository.findByMajorAndActive(major, true).stream()
                .map(this::mapEntityToResponseDto)
                .collect(Collectors.toList());
    }

    public StudentResponseDTO updateStudent(String id, StudentRequestDTO requestDTO) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        // Check for email uniqueness (if email is being changed)
        if (!student.getEmail().equals(requestDTO.getEmail()) &&
                studentRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + requestDTO.getEmail());
        }

        // Check for student code uniqueness (if code is being changed)
        if (!student.getStudentCode().equals(requestDTO.getStudentCode()) &&
                studentRepository.existsByStudentCode(requestDTO.getStudentCode())) {
            throw new IllegalArgumentException("Student code already in use: " + requestDTO.getStudentCode());
        }

        // Update student data
        mapDtoToEntity(requestDTO, student);
        Student updatedStudent = studentRepository.save(student);
        return mapEntityToResponseDto(updatedStudent);
    }

    public void deleteStudent(String id) {
        if (!studentRepository.existsById(id)) {
            throw new EntityNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    public StudentResponseDTO deactivateStudent(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        student.setActive(false);
        Student updatedStudent = studentRepository.save(student);
        return mapEntityToResponseDto(updatedStudent);
    }

    public StudentResponseDTO activateStudent(String id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id));

        student.setActive(true);
        Student updatedStudent = studentRepository.save(student);
        return mapEntityToResponseDto(updatedStudent);
    }

    // Helper method to map DTO to entity
    private void mapDtoToEntity(StudentRequestDTO dto, Student entity) {
        entity.setStudentCode(dto.getStudentCode());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhone(dto.getPhone());
        entity.setAddress(dto.getAddress());
        entity.setDateOfBirth(dto.getDateOfBirth());
        entity.setMajor(dto.getMajor());
        entity.setEnrollmentYear(dto.getEnrollmentYear());

        if (dto.getGpa() != null) {
            entity.setGpa(dto.getGpa());
        }
    }

    // Helper method to map entity to response DTO
    private StudentResponseDTO mapEntityToResponseDto(Student entity) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(entity.getId());
        dto.setStudentCode(entity.getStudentCode());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setPhone(entity.getPhone());
        dto.setAddress(entity.getAddress());
        dto.setDateOfBirth(entity.getDateOfBirth());
        dto.setMajor(entity.getMajor());
        dto.setEnrollmentYear(entity.getEnrollmentYear());
        dto.setGpa(entity.getGpa());
        dto.setActive(entity.isActive());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}