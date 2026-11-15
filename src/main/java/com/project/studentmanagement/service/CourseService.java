package com.project.studentmanagement.service;

import com.project.studentmanagement.dto.CourseDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    CourseDto createCourse(CourseDto courseDto);
    boolean existsByCode(String code);
    boolean existsByCourseCodeIgnoreCaseAndIdNot(String code,Long id);
    Page<CourseDto> getCourses(int page, int size);
    CourseDto getCourseById(Long id);
    CourseDto updateCourse(Long id, CourseDto courseDto);
    List<CourseDto> getAllCourses();
}
