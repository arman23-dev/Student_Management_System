package com.project.studentmanagement.service;


import com.project.studentmanagement.dto.StudentDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StudentService {

    boolean existsByEmail(String email);

    StudentDto createStudent(StudentDto studentDto);

    Page<StudentDto> getStudents(int page, int size);

    StudentDto getStudentById(Long id);

    boolean existByEmailIgnoreCaseAndNot(String email,Long id);

    StudentDto updateStudent(Long id, StudentDto studentDto);

    List<StudentDto> getAllStudents();


}
