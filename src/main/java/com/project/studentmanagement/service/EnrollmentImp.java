package com.project.studentmanagement.service;

import com.project.studentmanagement.dto.CourseDto;
import com.project.studentmanagement.dto.EnrollmentDto;
import com.project.studentmanagement.dto.EnrollmentSummaryDto;
import com.project.studentmanagement.dto.StudentDto;
import com.project.studentmanagement.model.Course;
import com.project.studentmanagement.model.Enrollment;
import com.project.studentmanagement.model.Student;
import com.project.studentmanagement.repository.CourseRepository;
import com.project.studentmanagement.repository.EnrollmentRepository;
import com.project.studentmanagement.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentImp implements EnrollementService{

    private EnrollmentRepository enrollmentRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private ModelMapper mapper;

    EnrollmentImp (EnrollmentRepository enrollmentRepository,
                   StudentRepository studentRepository,
                   CourseRepository courseRepository,
                   ModelMapper mapper){
        this.enrollmentRepository=enrollmentRepository;
        this.studentRepository=studentRepository;
        this.courseRepository=courseRepository;
        this.mapper=mapper;
    }
    @Override
    public void enrollStudentToCourses(EnrollmentDto enrollmentDto) {
        Student student =studentRepository.findById(enrollmentDto.getStudentId())
                .orElseThrow(()->new RuntimeException("Student not found"));

        for(Long courseId: enrollmentDto.getCourseIds()) {
            Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course not found"));

            if (enrollmentRepository.existsByStudentIdAndCourseId(enrollmentDto.getStudentId(), courseId)) {
                continue;
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);

            enrollmentRepository.save(enrollment);
        }

    }

    @Override
    public Page<EnrollmentSummaryDto> getEnrolledStudents(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC.DESC,"id");
        return studentRepository.findEnrollerdStudents(pageRequest)
                .map(student -> {
                    EnrollmentSummaryDto dto = new EnrollmentSummaryDto();
                    dto.setStudentId(student.getId());
                    dto.setEmail(student.getEmail());
                    dto.setStudentName(student.getFirstName() +" "+student.getLastName());
                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalfee = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getFee())
                            .filter(fee->fee !=null)
                            .reduce(BigDecimal.ZERO,BigDecimal::add);

                    dto.setTotalFee(totalfee);

                    return dto;
                });

    }

    @Override
    public EnrollmentSummaryDto findEnrolledStudentCourseDetails(Long studentId) {
        return studentRepository.findEnrolledStudentCourseDetails(studentId)
                .map(student -> {
                    EnrollmentSummaryDto dto = new EnrollmentSummaryDto();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstName() + " "+student.getLastName());
                    dto.setEmail(student.getEmail());

                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getFee())
                            .filter(fee -> fee != null)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    dto.setTotalFee(totalFee);

                    List<CourseDto> courseList = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse())
                            .map(course -> mapper.map(course, CourseDto.class))
                            .collect(Collectors.toList());

                    dto.setCourseList(courseList);

                    return dto;
                })
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    @Override
    public List<EnrollmentSummaryDto> getRecentlyEnrolledStudents() {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.DESC.DESC,"id");
        return studentRepository.findEnrollerdStudents(pageRequest)
                .map(student -> {
                    EnrollmentSummaryDto dto = new EnrollmentSummaryDto();
                    dto.setStudentId(student.getId());
                    dto.setEmail(student.getEmail());
                    dto.setStudentName(student.getFirstName() +" "+student.getLastName());
                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalfee = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getFee())
                            .filter(fee->fee !=null)
                            .reduce(BigDecimal.ZERO,BigDecimal::add);

                    dto.setTotalFee(totalfee);

                    return dto;
                })
                .getContent();
    }
}
