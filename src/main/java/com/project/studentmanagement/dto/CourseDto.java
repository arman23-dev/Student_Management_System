package com.project.studentmanagement.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CourseDto {

    private Long id;
    @NotBlank(message = "Course name is required")
    @Size(max = 150, message = "Max. 150 character allowed")
    private String courseName;
    @NotBlank(message = "Course code is requird")
    private String courseCode;
    @NotBlank(message = "Course duration is required")
    private String duration;
    @NotNull(message = "Course fee required")
    private BigDecimal fee;
    @Size(max = 500, message = "Max. 500 character allowed")
    private String description;

    private boolean active;

}
