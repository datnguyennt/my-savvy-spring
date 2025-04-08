package com.savvycom.my_savvy_spring.repository;

import com.savvycom.my_savvy_spring.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    Optional<Student> findByStudentCode(String studentCode);

    Optional<Student> findByEmail(String email);

    List<Student> findByLastNameContainingIgnoreCaseOrFirstNameContainingIgnoreCase(String lastName, String firstName);

    List<Student> findByMajorAndActive(String major, boolean active);

    boolean existsByStudentCode(String studentCode);

    boolean existsByEmail(String email);
}