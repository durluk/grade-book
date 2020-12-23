package com.rudkul.grade.book.business.api;

import com.rudkul.grade.book.dto.SubjectGradesDTO;
import com.rudkul.grade.book.entity.SchoolSubject;
import com.rudkul.grade.book.entity.Student;

import java.util.List;

/**
 * Service for handling grades
 */
public interface GradeService {

    /**
     * Service will return all grades for student with given technical id. Grades will be grouped by subject name
     *
     * @param studentId technical id of student for which grade will be searched
     * @return list of grades
     */
    List<SubjectGradesDTO> getGradesForGivenStudent(Long studentId);

    /**
     * Creates grade for given student, from given subject and value.
     *
     * @param student       which obtains grade
     * @param schoolSubject subject from which grade is
     * @param gradeValue    value of grade
     */
    void registerGrade(Student student, SchoolSubject schoolSubject, String gradeValue);
}
