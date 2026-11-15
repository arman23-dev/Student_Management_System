package com.project.studentmanagement.repository;

import com.project.studentmanagement.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {


    boolean existsByCourseCodeIgnoreCase(String code);

    boolean existsByCourseCodeIgnoreCaseAndIdNot(String code,Long id);

    Page<Course> findByActiveTrue(Pageable pageable);

    List<Course> findByActiveTrue(Sort sot);


}
