package com.rudkul.grade.book.business.api;

import com.rudkul.grade.book.dto.GradeToRegisterDTO;
import com.rudkul.grade.book.dto.StudentBasicInfoDTO;
import com.rudkul.grade.book.dto.StudentFullInfoDTO;
import com.rudkul.grade.book.dto.StudentToRegisterDTO;
import com.rudkul.grade.book.dto.StudentWithGradesDTO;
import com.rudkul.grade.book.util.exception.NotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * Service for students
 */
public interface StudentService {

    /**
     * Creates and saves student from given list. Based on pesel ignores duplicates.
     *
     * @param studentToRegisterList list from which students will be created
     */
    void registerStudents(List<StudentToRegisterDTO> studentToRegisterList);

    /**
     * Returns all persisted students.
     *
     * @return list of all students
     */
    List<StudentBasicInfoDTO> getAll();

    /**
     * Returns student with given uuid. Throws {@link NotFoundException} if such
     * student does not exists.
     *
     * @param id searched uuid
     * @return found student
     */
    StudentFullInfoDTO get(UUID id);

    /**
     * Returns student with given uuid and his grades. Throws {@link NotFoundException}
     * if such student does not exists.
     *
     * @param id searched uuid
     * @return found student with grades
     */
    StudentWithGradesDTO getStudentGrades(UUID id);

    /**
     * Creates grades for given student. Both student with given id, and subject for which grades have to be created must
     * exists in database, otherwise {@link NotFoundException} will be thrown.
     *
     * @param studentId        id of student for which grades are.
     * @param gradesToRegister grades with subject names from which they are
     */
    void giveGrade(UUID studentId, List<GradeToRegisterDTO> gradesToRegister);
}
