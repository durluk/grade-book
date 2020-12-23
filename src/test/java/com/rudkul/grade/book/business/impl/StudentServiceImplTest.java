package com.rudkul.grade.book.business.impl;

import com.rudkul.grade.book.business.api.GradeService;
import com.rudkul.grade.book.business.api.SchoolSubjectService;
import com.rudkul.grade.book.dataaccess.dao.StudentRepository;
import com.rudkul.grade.book.dto.GradeToRegisterDTO;
import com.rudkul.grade.book.dto.StudentBasicInfoDTO;
import com.rudkul.grade.book.dto.StudentFullInfoDTO;
import com.rudkul.grade.book.dto.StudentToRegisterDTO;
import com.rudkul.grade.book.dto.StudentWithGradesDTO;
import com.rudkul.grade.book.dto.SubjectGradesDTO;
import com.rudkul.grade.book.entity.Address;
import com.rudkul.grade.book.entity.SchoolSubject;
import com.rudkul.grade.book.entity.SchoolYear;
import com.rudkul.grade.book.entity.Student;
import com.rudkul.grade.book.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.util.CollectionUtils.isEmpty;

@ExtendWith({SpringExtension.class})
class StudentServiceImplTest {

    @InjectMocks
    private StudentServiceImpl studentService;

    @Mock
    private StudentRepository repository;

    @Mock
    private GradeService gradeService;

    @Mock
    private SchoolSubjectService schoolSubjectService;

    @Captor
    private ArgumentCaptor<Student> studentCaptor;

    @Test
    void registerStudent_ForValidInput_ShouldCreateAndSave() {
        // given
        StudentToRegisterDTO studentToRegister = createStudentToRegisterDTO();
        // when
        studentService.registerStudents(Collections.singletonList(studentToRegister));

        // then
        verify(repository).save(studentCaptor.capture());
        assertCapturedStudent(studentToRegister);
    }

    @Test
    void registerStudent_ForEmptyInput_ShouldNotCreateAndSave() {
        // given when
        studentService.registerStudents(emptyList());

        // then
        verify(repository, never()).save(any());
    }

    @Test
    void getAll_StudentsExistsInRepository_ShouldFetchAndMapToDTOs() {
        // given
        Student student = createStudent();
        List<Student> students = asList(student, student);
        when(repository.findAll()).thenReturn(students);
        // when
        List<StudentBasicInfoDTO> all = studentService.getAll();

        // then
        assertFalse(isEmpty(all));
        assertEquals(2, all.size());
    }

    @Test
    void getAll_StudentsNotExistsInRepository_ShouldFetchEmptyListAndMapToEmptyDTOList() {
        // given
        when(repository.findAll()).thenReturn(emptyList());

        // when
        List<StudentBasicInfoDTO> all = studentService.getAll();

        // then
        assertTrue(isEmpty(all));
    }

    @Test
    void get_StudentsWithGivenUuidExists_ShouldFetchAndReturnMappedStudent() {
        // given
        UUID studentUuid = UUID.randomUUID();
        Student student = createStudent();
        when(repository.findStudentByExternalId(eq(studentUuid))).thenReturn(student);

        // when
        StudentFullInfoDTO studentFullInfoDTO = studentService.get(studentUuid);

        // then
        verify(repository).findStudentByExternalId(eq(studentUuid));
        assertNotNull(studentFullInfoDTO);
    }

    @Test
    void get_StudentsWithGivenUuidDoesNotExists_ShouldThrowNotFoundException() {
        // given
        UUID studentUuid = UUID.randomUUID();
        when(repository.findStudentByExternalId(eq(studentUuid))).thenReturn(null);

        // when then
        assertThrows(NotFoundException.class, () -> studentService.get(studentUuid));
        verify(repository).findStudentByExternalId(eq(studentUuid));
    }

    @Test
    void getStudentGrades_StudentsWithGivenUuidExists_ShouldMapStudentAndGradesIntoDTOAndReturn() {
        // given
        UUID studentUuid = UUID.randomUUID();
        Student student = createStudent();
        Long id = 1L;
        student.setId(id);
        when(repository.findStudentByExternalId(eq(studentUuid))).thenReturn(student);
        List<SubjectGradesDTO> expectedGrades = singletonList(new SubjectGradesDTO("Biologia", asList("1", "2")));
        when(gradeService.getGradesForGivenStudent(id)).thenReturn(expectedGrades);

        // when
        StudentWithGradesDTO studentGrades = studentService.getStudentGrades(studentUuid);

        // then
        verify(repository).findStudentByExternalId(eq(studentUuid));
        assertNotNull(studentGrades);
        List<SubjectGradesDTO> grades = studentGrades.getGrades();
        assertFalse(isEmpty(grades));
        assertEquals(1, grades.size());
        List<String> gradesValues = grades.get(0).getGrades();
        assertFalse(isEmpty(gradesValues));
        assertEquals(2, gradesValues.size());
    }

    @Test
    void getStudentGrades_StudentsWithGivenUuidDoesNotExists_ShouldThrowNotFoundException() {
        // given
        UUID studentUuid = UUID.randomUUID();
        when(repository.findStudentByExternalId(eq(studentUuid))).thenReturn(null);

        // when then
        assertThrows(NotFoundException.class, () -> studentService.getStudentGrades(studentUuid));
        verify(repository).findStudentByExternalId(eq(studentUuid));
    }

    @Test
    void giveGrade_StudentsWithGivenUuidExists_ShouldCreateAndSaveGrade() {
        // given
        UUID studentUuid = UUID.randomUUID();
        when(repository.findStudentByExternalId(eq(studentUuid))).thenReturn(createStudent());
        when(schoolSubjectService.get(anyString())).thenReturn(new SchoolSubject());
        GradeToRegisterDTO gradeToRegisterDTO = createGradeToRegister();

        // when
        studentService.giveGrade(studentUuid, singletonList(gradeToRegisterDTO));

        // then
        verify(repository).findStudentByExternalId(eq(studentUuid));
        verify(schoolSubjectService).get(anyString());
        verify(gradeService).registerGrade(any(Student.class), any(SchoolSubject.class), anyString());
    }

    @Test
    void giveGrade_StudentsWithGivenUuidDoesNotExists_ShouldThrowNotFoundException() {
        // given
        UUID studentUuid = UUID.randomUUID();
        when(repository.findStudentByExternalId(eq(studentUuid))).thenReturn(null);

        // when then
        assertThrows(NotFoundException.class, () -> studentService.giveGrade(studentUuid, singletonList(createGradeToRegister())));
        verify(repository).findStudentByExternalId(eq(studentUuid));
        verify(schoolSubjectService, never()).get(anyString());
        verify(gradeService, never()).registerGrade(any(Student.class), any(SchoolSubject.class), anyString());
    }

    private GradeToRegisterDTO createGradeToRegister() {
        GradeToRegisterDTO gradeToRegisterDTO = new GradeToRegisterDTO();
        gradeToRegisterDTO.setSubject("Matematyka");
        gradeToRegisterDTO.setGrade("B");
        return gradeToRegisterDTO;
    }

    private Student createStudent() {
        Student student = new Student();
        student.setSchoolYear(SchoolYear.FIFTH);
        return student;
    }

    private void assertCapturedStudent(StudentToRegisterDTO studentToRegister) {
        Student student = studentCaptor.getValue();
        assertNotNull(student);
        assertEquals(studentToRegister.getFirstName(), student.getFirstName());
        assertEquals(studentToRegister.getLastName(), student.getLastName());
        assertEquals(studentToRegister.getPesel(), student.getPesel());
        assertEquals(studentToRegister.getBirthDate(), student.getBirthDate().toString());
        assertEquals(studentToRegister.getDyslexic(), student.getDyslexic().toString());
        assertEquals(studentToRegister.getSchoolYear(), student.getSchoolYear().getNumberRepresentation().toString());
        Address address = student.getAddress();
        assertEquals(studentToRegister.getStreet(), address.getStreet());
        assertEquals(studentToRegister.getBuildingNumber(), address.getBuildingNumber());
        assertEquals(studentToRegister.getApartmentNumber(), address.getApartmentNumber());
        assertEquals(studentToRegister.getPostalCode(), address.getPostalCode());
        assertEquals(studentToRegister.getCity(), address.getCity());
    }

    private StudentToRegisterDTO createStudentToRegisterDTO() {
        LocalDate birthDate = LocalDate.now();
        StudentToRegisterDTO studentToRegister = new StudentToRegisterDTO();
        studentToRegister.setFirstName("Adam");
        studentToRegister.setLastName("Nowak");
        studentToRegister.setPesel("12345678912");
        studentToRegister.setBirthDate(birthDate.toString());
        studentToRegister.setDyslexic("true");
        studentToRegister.setSchoolYear("1");
        studentToRegister.setStreet("Pocztowa");
        studentToRegister.setBuildingNumber("12A");
        studentToRegister.setApartmentNumber("1");
        studentToRegister.setPostalCode("11-111");
        studentToRegister.setCity("Poznań");
        return studentToRegister;
    }
}