package com.rudkul.grade.book.it;

import com.rudkul.grade.book.business.api.GradeService;
import com.rudkul.grade.book.business.api.SchoolSubjectService;
import com.rudkul.grade.book.business.api.StudentService;
import com.rudkul.grade.book.business.impl.GradeServiceImpl;
import com.rudkul.grade.book.business.impl.SchoolSubjectServiceImpl;
import com.rudkul.grade.book.business.impl.StudentServiceImpl;
import com.rudkul.grade.book.dataaccess.dao.GradeRepository;
import com.rudkul.grade.book.dataaccess.dao.SchoolSubjectRepository;
import com.rudkul.grade.book.dataaccess.dao.StudentRepository;
import com.rudkul.grade.book.dto.StudentBasicInfoDTO;
import com.rudkul.grade.book.dto.StudentToRegisterDTO;
import com.rudkul.grade.book.entity.Address;
import com.rudkul.grade.book.entity.SchoolYear;
import com.rudkul.grade.book.entity.Student;
import com.rudkul.grade.book.it.repository.GradeRepositoryTestImpl;
import com.rudkul.grade.book.it.repository.SchoolSubjectRepositoryTestImpl;
import com.rudkul.grade.book.it.repository.StudentRepositoryTestImpl;
import com.rudkul.grade.book.rest.StudentController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.util.CollectionUtils.isEmpty;

public class StudentFlowIntegrationTest {

    private final StudentRepository studentRepository = new StudentRepositoryTestImpl();
    private final GradeRepository gradeRepository = new GradeRepositoryTestImpl();
    private final GradeService gradeService = new GradeServiceImpl(gradeRepository);
    private final SchoolSubjectRepository schoolSubjectRepository = new SchoolSubjectRepositoryTestImpl();
    private final SchoolSubjectService schoolSubjectService = new SchoolSubjectServiceImpl(schoolSubjectRepository);
    private final StudentService studentService = new StudentServiceImpl(studentRepository, gradeService, schoolSubjectService);
    private final StudentController studentController = new StudentController(studentService);

    @BeforeEach
    public void setUp() {
        studentRepository.deleteAll();
        schoolSubjectRepository.deleteAll();
        gradeRepository.deleteAll();
    }

    @Test
    void name() {
        // given
        createAndSaveStudentForTest();

        // when
        List<StudentBasicInfoDTO> allStudents = studentController.getAllStudents();

        // then
        assertFalse(isEmpty(allStudents));
        assertEquals(1, allStudents.size());
    }

    @Test
    void name234() {
        // given
        StudentToRegisterDTO studentToRegisterDTO = new StudentToRegisterDTO();
        studentToRegisterDTO.setSchoolYear("3");
        studentToRegisterDTO.setFirstName("Jerzy");
        studentToRegisterDTO.setCity("Gdańsk");
        studentToRegisterDTO.setBirthDate(LocalDate.now().toString());
        // when
        studentController.createStudents(Collections.singletonList(studentToRegisterDTO));

        // then
        Iterable<Student> all = studentRepository.findAll();
        Student student = all.iterator().next();
        assertNotNull(student);
        assertEquals(studentToRegisterDTO.getFirstName(), student.getFirstName());
        Address address = student.getAddress();
        assertNotNull(address);
        assertEquals(studentToRegisterDTO.getCity(), address.getCity());
    }

    private void createAndSaveStudentForTest() {
        Student student = new Student();
        student.setFirstName("Tomasz");
        student.setSchoolYear(SchoolYear.EIGHTH);
        studentRepository.save(student);
    }
}
