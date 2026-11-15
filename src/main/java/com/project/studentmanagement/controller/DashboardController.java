package com.project.studentmanagement.controller;

import com.project.studentmanagement.repository.CourseRepository;
import com.project.studentmanagement.repository.EnrollmentRepository;
import com.project.studentmanagement.repository.StudentRepository;
import com.project.studentmanagement.service.CourseServiceImp;
import com.project.studentmanagement.service.DashboardServiceImp;
import com.project.studentmanagement.service.EnrollmentImp;
import com.project.studentmanagement.service.StudentServiceImp;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private EnrollmentImp enrollmentImp;
    private DashboardServiceImp dashboardServiceImp;

    DashboardController (EnrollmentImp enrollmentImp,
                         DashboardServiceImp dashboardServiceImp){
        this.enrollmentImp = enrollmentImp;
        this.dashboardServiceImp=dashboardServiceImp;

    }
    @GetMapping("/dashboard")
    public String dashboard(Model model){

        model.addAttribute("dashboardStats",dashboardServiceImp.getDashboardStats());
        model.addAttribute("students",enrollmentImp.getRecentlyEnrolledStudents());
        return "dashboard";
    }
}
