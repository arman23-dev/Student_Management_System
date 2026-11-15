package com.project.studentmanagement.controller;

import com.project.studentmanagement.dto.CourseDto;
import com.project.studentmanagement.dto.EnrollmentDto;
import com.project.studentmanagement.dto.EnrollmentSummaryDto;
import com.project.studentmanagement.service.CourseServiceImp;
import com.project.studentmanagement.service.EnrollmentImp;
import com.project.studentmanagement.service.StudentServiceImp;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {
    private StudentServiceImp studentServiceImp;
    private CourseServiceImp courseServiceImp;
    private EnrollmentImp enrollmentImp;

    EnrollmentController(StudentServiceImp studentServiceImp,
                         CourseServiceImp courseServiceImp,
                         EnrollmentImp enrollmentImp){
        this.studentServiceImp=studentServiceImp;
        this.courseServiceImp=courseServiceImp;
        this.enrollmentImp=enrollmentImp;

    }

    @GetMapping("/showEnroll")
    public String showEnroll(Model model){
        model.addAttribute("enrollmentDto", new EnrollmentDto());
        model.addAttribute("courseList",courseServiceImp.getAllCourses());
        model.addAttribute("studentList",studentServiceImp.getAllStudents());
        return "enroll-course";
    }
    @GetMapping("/enrollmentList")
    public String enrollmentList(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "3") int size,
                                 Model model,
                                 @RequestParam(value="message", required = false) String message)
    {


        Page<EnrollmentSummaryDto> students = enrollmentImp.getEnrolledStudents(page, size);
        model.addAttribute("students", students);
        model.addAttribute("message", message);

        return "enrolled-students";
    }
    @PostMapping("/enrollCourse")
    public String enrollCourse(@Valid @ModelAttribute("enrollmentDto") EnrollmentDto enrollmentDto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            model.addAttribute("courseList",courseServiceImp.getAllCourses());
            model.addAttribute("studentList",studentServiceImp.getAllStudents());
            return "enroll-course";
        }
        enrollmentImp.enrollStudentToCourses(enrollmentDto);

        redirectAttributes.addAttribute("message","Enrollment Successfully");
        return "redirect:/enrollments/enrollmentList";
    }
    @GetMapping("/getStudentEnrollmentDetails/{id}")
    public String getStudentEnrollmentDetails(@PathVariable Long id, Model model,
                                              @RequestParam(defaultValue = "enrollments") String source) {

        EnrollmentSummaryDto enrollmentSummaryDTO
                = enrollmentImp.findEnrolledStudentCourseDetails(id);

        model.addAttribute("enrollmentSummaryDTO", enrollmentSummaryDTO);
        model.addAttribute("source", source);

        return "enrollment-details";

    }

}
