package com.project.studentmanagement.service;

import com.project.studentmanagement.dto.CourseDto;
import com.project.studentmanagement.model.Course;
import com.project.studentmanagement.repository.CourseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImp implements CourseService{
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    CourseServiceImp(CourseRepository courseRepository,ModelMapper modelMapper){
        this.courseRepository = courseRepository;
        this.mapper=modelMapper;
    }
    @Override
    public CourseDto createCourse(CourseDto courseDto) {
        Course course = mapper.map(courseDto,Course.class);
        courseRepository.save(course);
        return mapper.map(course, CourseDto.class);
    }

    @Override
    public boolean existsByCode(String code) {
        return courseRepository.existsByCourseCodeIgnoreCase(code);
    }

    @Override
    public boolean existsByCourseCodeIgnoreCaseAndIdNot(String code, Long id) {
        return courseRepository.existsByCourseCodeIgnoreCaseAndIdNot(code, id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourseDto> getCourses(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC.DESC,"id");

        return courseRepository.findByActiveTrue(pageRequest)
                .map(course -> mapper.map(course,CourseDto.class));

    }

    @Override
    @Transactional(readOnly = true)
    public CourseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("No Course Found"));
        return mapper.map(course,CourseDto.class);
    }

    @Override
    public CourseDto updateCourse(Long id, CourseDto courseDto) {
        Course course = courseRepository.findById(id)
                .orElseThrow(()->new RuntimeException("No course found"));


        mapper.map(courseDto,course);
        Course updated = courseRepository.save(course);

        return mapper.map(updated,CourseDto.class);
    }

    @Override
    public List<CourseDto> getAllCourses() {

        return courseRepository.findByActiveTrue(Sort.by("courseName"))
                .stream()
                .map(course -> mapper.map(course,CourseDto.class))
                .collect(Collectors.toList());
    }

}
