package com.project.studentmanagement.service;

import com.project.studentmanagement.dto.DashboardDto;
import com.project.studentmanagement.repository.CourseRepository;
import com.project.studentmanagement.repository.EnrollmentRepository;
import com.project.studentmanagement.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImp implements DashboardService{

    private EnrollmentRepository enrollmentRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;


    DashboardServiceImp (EnrollmentRepository enrollmentRepository,
                   StudentRepository studentRepository,
                   CourseRepository courseRepository){
        this.enrollmentRepository=enrollmentRepository;
        this.studentRepository=studentRepository;
        this.courseRepository=courseRepository;

    }

    @Override
    public DashboardDto getDashboardStats() {
        long totalStudents = studentRepository.count();
        long totalCourse = courseRepository.count();
        String topPerformingCourse = getTopPerformingCourse();

        YearMonth month = YearMonth.now();
        LocalDateTime startDate = month.atDay(1).atStartOfDay();
        LocalDateTime endDate = month.atEndOfMonth().atTime(LocalTime.MAX);
        long studentEnrolledthisMonth = enrollmentRepository.countDistinctStudentByEnrollDateBetween(startDate,endDate);

        DashboardDto dashboardDto = new DashboardDto();
        dashboardDto.setTotalStudents(totalStudents);
        dashboardDto.setTotalCourses(totalCourse);
        dashboardDto.setTopPerformingCourse(topPerformingCourse);
        dashboardDto.setStudentsEnrolledThisMonth(studentEnrolledthisMonth);
        return dashboardDto;
    }
    private String getTopPerformingCourse(){

        return enrollmentRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(e -> e.getCourse().getCourseName(),Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("N/A");
    }
}
