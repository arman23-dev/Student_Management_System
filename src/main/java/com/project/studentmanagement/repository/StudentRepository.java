package com.project.studentmanagement.repository;

import com.project.studentmanagement.dto.StudentDto;
import com.project.studentmanagement.model.Course;
import com.project.studentmanagement.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    boolean existsByEmailIgnoreCase(String email);
    Page<Student> findByActiveTrue(Pageable pageable);
    boolean existsByEmailIgnoreCaseAndIdNot(String code,Long id);
    List<Student> findByActiveTrue();

    @EntityGraph(attributePaths = {"enrollments","enrollments.course"})
    @Query(value = """
           Select distinct s 
           from Student s
           join s.enrollments e 
           """,
            countQuery = """
                   Select count(distinct s) 
                   from Student s
                   join s.enrollments e 
            """
    )
    Page<Student> findEnrollerdStudents(Pageable pageable);

    @Query("""
            Select s
            from Student s
            join fetch s.enrollments e
            join fetch e.course c
            where s.id= :id
            """)
    Optional<Student> findEnrolledStudentCourseDetails(@Param("id") Long id);




}
