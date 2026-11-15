package com.project.studentmanagement.service;

import com.project.studentmanagement.dto.StudentDto;
import com.project.studentmanagement.model.Student;
import com.project.studentmanagement.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImp implements StudentService{

    private final StudentRepository studentRepository;
    private final ModelMapper mapper;
    StudentServiceImp (StudentRepository studentRepository,
                       ModelMapper mapper){
        this.studentRepository=studentRepository;
        this.mapper =mapper;
    }
    @Override
    public boolean existsByEmail(String email) {
        return studentRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public StudentDto createStudent(StudentDto studentDto) {
        Student student = mapper.map(studentDto,Student.class);
        Student savedstudent = studentRepository.save(student);
        return mapper.map(savedstudent,StudentDto.class);
    }

    @Override
    public Page<StudentDto> getStudents(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC.DESC,"id");

        return studentRepository.findByActiveTrue(pageRequest)
                .map(student -> mapper.map(student, StudentDto.class));
    }

    @Override
    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No Student found"));
        return mapper.map(student,StudentDto.class);
    }

    @Override
    public boolean existByEmailIgnoreCaseAndNot(String email, Long id) {

        return studentRepository.existsByEmailIgnoreCaseAndIdNot(email,id);
    }

    @Override
    public StudentDto updateStudent(Long id, StudentDto studentDto) {
        Student student = studentRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No Student is found"));

        Student updated = studentRepository.save(student);

        return mapper.map(student,StudentDto.class);
    }
    @Override
    public List<StudentDto> getAllStudents() {

        return studentRepository.findByActiveTrue()
                .stream()
                .map(students -> mapper.map(students,StudentDto.class))
                .collect(Collectors.toList());
    }


}
