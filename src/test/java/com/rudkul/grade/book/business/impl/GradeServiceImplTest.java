package com.rudkul.grade.book.business.impl;

import com.rudkul.grade.book.dataaccess.dao.GradeRepository;
import com.rudkul.grade.book.dto.SubjectGradesDTO;
import com.rudkul.grade.book.entity.Grade;
import com.rudkul.grade.book.entity.SchoolSubject;
import com.rudkul.grade.book.entity.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.util.CollectionUtils.isEmpty;

@ExtendWith({SpringExtension.class})
class GradeServiceImplTest {

    @InjectMocks
    private GradeServiceImpl gradeService;

    @Mock
    private GradeRepository gradeRepository;

    @Captor
    private ArgumentCaptor<Grade> gradeCaptor;

    @Test
    void registerGrade_ForValidInput_ShouldCreateAndSaveGrade() {
        // given
        Student student = new Student();
        SchoolSubject schoolSubject = new SchoolSubject();
        String gradeValue = "1";

        // when
        gradeService.registerGrade(student, schoolSubject, gradeValue);

        // then
        verify(gradeRepository).save(gradeCaptor.capture());
        Grade grade = gradeCaptor.getValue();
        assertNotNull(grade);
        assertEquals(student, grade.getStudent());
        assertEquals(schoolSubject, grade.getSchoolSubject());
        assertEquals(gradeValue, grade.getValue());
    }

    @Test
    void getGradesForGivenStudent_ForStudentWithExistingGrades_ShouldReturnGradesGroupedBySubjectName() {
        // given
        Long studentId = 1L;
        String subjectName = "Biologia";
        Grade grade1 = createGrade("1", subjectName);
        Grade grade2 = createGrade("3", subjectName);
        Grade grade3 = createGrade("5", "Matematyka");
        when(gradeRepository.findGradeByStudentId(anyLong())).thenReturn(asList(grade1, grade2, grade3));

        // when
        List<SubjectGradesDTO> gradesForGivenStudent = gradeService.getGradesForGivenStudent(studentId);

        // then
        verify(gradeRepository).findGradeByStudentId(eq(studentId));
        assertFalse(isEmpty(gradesForGivenStudent));
        assertEquals(2, gradesForGivenStudent.size());
    }

    @Test
    void getGradesForGivenStudent_ForStudentWithNotExistingGrades_ShouldReturnEmptyList() {
        // given
        Long studentId = 1L;
        when(gradeRepository.findGradeByStudentId(anyLong())).thenReturn(emptyList());

        // when
        List<SubjectGradesDTO> gradesForGivenStudent = gradeService.getGradesForGivenStudent(studentId);

        // then
        verify(gradeRepository).findGradeByStudentId(eq(studentId));
        assertTrue(isEmpty(gradesForGivenStudent));
    }

    private Grade createGrade(String gradeValue, String subjectName) {
        Grade grade = new Grade();
        grade.setValue(gradeValue);
        grade.setSchoolSubject(createSchoolSubject(subjectName));
        return grade;
    }

    private SchoolSubject createSchoolSubject(String subjectName) {
        SchoolSubject schoolSubject = new SchoolSubject();
        schoolSubject.setName(subjectName);
        return schoolSubject;
    }
}