package com.project.studentmanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DashboardDto {
    private long totalStudents;
    private long totalCourses;
    private String topPerformingCourse;
    private long studentsEnrolledThisMonth;
}
