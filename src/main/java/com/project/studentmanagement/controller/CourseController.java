package com.project.studentmanagement.controller;

import com.project.studentmanagement.dto.CourseDto;
import com.project.studentmanagement.exception.GlobalExceptionHandler;
import com.project.studentmanagement.service.CourseServiceImp;
import jakarta.validation.Valid;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/course")
public class CourseController {
    private static final Logger Log = LoggerFactory.getLogger(CourseController.class);

    private final CourseServiceImp courseServiceImp;
    CourseController (CourseServiceImp courseServiceImp){
        this.courseServiceImp=courseServiceImp;
    }

    @GetMapping("/new")
    public  String showCreateCourse(Model model){
        Log.info("GET /course/new - showing create course page");
        model.addAttribute("courseDto",new CourseDto());
        return "add-course";
    }
    @GetMapping("/list")
    public String listCourses(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "3") int size,
                              Model model,
                              @RequestParam(value = "message" ,required = false) String message){
        Page<CourseDto> course= courseServiceImp.getCourses(page,size);
        model.addAttribute("course",course);
        model.addAttribute("message", message);

        return "courses";
    }
    @PostMapping
    public String listCourse(@Valid @ModelAttribute("courseDto") CourseDto courseDto,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes){
        Log.info("POST /course/ - create course request received");
        if(bindingResult.hasErrors()){
            Log.error("POST /course - page return due to validation error");
            return "add-course";
        }
        if(courseServiceImp.existsByCode(courseDto.getCourseCode())){
            Log.error("POST /course - code must be unique");
            bindingResult.rejectValue("courseCode",null,"Code must be unique");
            return "add-course";
        }

        courseServiceImp.createCourse(courseDto);
        redirectAttributes.addAttribute("message", "Course is created successfully");
        Log.info("POST /course - create course successfully");
        return "redirect:/course/list";
    }
    @GetMapping("/{id}")
    public String getCourseById(@PathVariable Long id, Model model){
        CourseDto course= courseServiceImp.getCourseById(id);
        model.addAttribute("course", course);

        return "view-course";

    }
    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable Long id, Model model){
        CourseDto course= courseServiceImp.getCourseById(id);
        model.addAttribute("courseDto", course);

        return "edit-course";

    }
    @PostMapping("/{id}/update")
    public String updateCourse(@PathVariable Long id,
                               @Valid @ModelAttribute("courseDto") CourseDto courseDto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){
        Log.info("POST /{id}/update - create course request received. {}",id);
        if(bindingResult.hasErrors()){
            Log.error("POST /{id}/update - page return due to validation error");
            return "edit-course";
        }
        if(courseServiceImp.existsByCourseCodeIgnoreCaseAndIdNot(courseDto.getCourseCode(),id)){
            Log.error("POST /{id}/update - code must be unique");
            bindingResult.rejectValue("courseCode",null,"Code must be unique");
            return "edit-course";
        }

        courseServiceImp.createCourse(courseDto);
        redirectAttributes.addAttribute("message", "Course is updated successfully");
        Log.info("POST /{id}/update - create course successfully");
        return "redirect:/course/list";
    }
}
