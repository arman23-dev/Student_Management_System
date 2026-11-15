package com.project.studentmanagement.controller;

import com.project.studentmanagement.dto.CourseDto;
import com.project.studentmanagement.dto.StudentDto;
import com.project.studentmanagement.service.StudentServiceImp;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentServiceImp studentServiceImp;

    StudentController(StudentServiceImp studentServiceImp){
        this.studentServiceImp = studentServiceImp;
    }
    @GetMapping("/new")
    public String showCreateStudent(Model model){
        model.addAttribute("studentDto", new StudentDto());
        return "add-student";
    }
    @GetMapping("/list")
    public String listStudent(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "3") int size,
                              Model model,
                              @RequestParam(value = "message" ,required = false) String message){

        Page<StudentDto> students= studentServiceImp.getStudents(page,size);
        model.addAttribute("students",students);
        model.addAttribute("message", message);

        return "students";
    }
    @PostMapping("/save")
    public String createStudent(@Valid @ModelAttribute("studentDto") StudentDto studentDto,
                                BindingResult bindingResult,
                                Model model,
                                RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            return "add-student";
        }
        if(studentServiceImp.existsByEmail(studentDto.getEmail())){
            bindingResult.rejectValue("email",null,"Email must be unique");
            return "add-student";
        }
        studentServiceImp.createStudent(studentDto);
        redirectAttributes.addAttribute("message","Student Created Successfully");

        return "redirect:students/list";
    }
    @GetMapping("/{id}")
    public String getStudentById(@PathVariable Long id, Model model){
        StudentDto student= studentServiceImp.getStudentById(id);
        model.addAttribute("student", student);

        return "view-student";
    }
    @GetMapping("/{id}/edit")
    public String editStudent(@PathVariable Long id, Model model){
        StudentDto student= studentServiceImp.getStudentById(id);
        model.addAttribute("studentDto", student);

        return "edit-student";
    }
    @PostMapping("/{id}/update")
    public String updateStudent(@PathVariable Long id,
                               @Valid @ModelAttribute("studentDto") StudentDto studentDto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){

            return "edit-student";
        }
        if(studentServiceImp.existByEmailIgnoreCaseAndNot(studentDto.getEmail(),studentDto.getId())){

            bindingResult.rejectValue("courseCode",null,"Code must be unique");
            return "edit-student";
        }

        studentServiceImp.updateStudent(id,studentDto);
        redirectAttributes.addAttribute("message", "Course is updated successfully");

        return "redirect:/course/list";
    }
}
