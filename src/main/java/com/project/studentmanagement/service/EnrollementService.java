package com.project.studentmanagement.service;

import com.project.studentmanagement.dto.EnrollmentDto;
import com.project.studentmanagement.dto.EnrollmentSummaryDto;
import com.project.studentmanagement.dto.StudentDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


public interface EnrollementService {
    void enrollStudentToCourses(EnrollmentDto enrollmentDto);

    Page<EnrollmentSummaryDto> getEnrolledStudents(int page, int size);

    EnrollmentSummaryDto findEnrolledStudentCourseDetails(Long studentid);

    List<EnrollmentSummaryDto> getRecentlyEnrolledStudents();

}
