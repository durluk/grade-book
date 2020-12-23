package com.rudkul.grade.book.business.impl;

import com.rudkul.grade.book.business.api.GradeService;
import com.rudkul.grade.book.business.api.SchoolSubjectService;
import com.rudkul.grade.book.business.api.StudentService;
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
import com.rudkul.grade.book.util.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;

/**
 * {@inheritDoc}
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    private final GradeService gradeService;

    private final SchoolSubjectService schoolSubjectService;

    @Autowired
    public StudentServiceImpl(@Qualifier("studentRepository") StudentRepository repository,
                              GradeService gradeService,
                              SchoolSubjectService schoolSubjectService) {
        this.repository = repository;
        this.gradeService = gradeService;
        this.schoolSubjectService = schoolSubjectService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerStudents(List<StudentToRegisterDTO> studentToRegisterList) {
        for (StudentToRegisterDTO studentToRegister : studentToRegisterList) {
            if (isStudentWithGivenPeselDoesNotExists(studentToRegister)) {
                Student student = createStudent(studentToRegister);
                save(student);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<StudentBasicInfoDTO> getAll() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .map(this::mapToStudentBasicInfoDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentFullInfoDTO get(UUID id) {
        Student foundStudent = getStudentWithId(id);
        return StudentMapper.INSTANCE.studentToStudentFullInfoDTO(foundStudent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StudentWithGradesDTO getStudentGrades(UUID id) {
        Student foundStudent = getStudentWithId(id);
        List<SubjectGradesDTO> gradesForGivenStudent = gradeService.getGradesForGivenStudent(foundStudent.getId());
        StudentBasicInfoDTO studentBasicInfoDTO = mapToStudentBasicInfoDTO(foundStudent);
        return new StudentWithGradesDTO(studentBasicInfoDTO, gradesForGivenStudent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void giveGrade(UUID studentId, List<GradeToRegisterDTO> gradesToRegister) {
        for (GradeToRegisterDTO gradeToRegister : gradesToRegister) {
            Student foundStudent = getStudentWithId(studentId);
            SchoolSubject schoolSubject = schoolSubjectService.get(gradeToRegister.getSubject());
            gradeService.registerGrade(foundStudent, schoolSubject, gradeToRegister.getGrade());
        }
    }

    private Student getStudentWithId(UUID studentId) {
        Student foundStudent = findStudentByExternalId(studentId);
        handleNotFound(studentId, foundStudent);
        return foundStudent;
    }

    private void handleNotFound(UUID id, Student foundStudent) {
        if (isNull(foundStudent)) {
            throw new NotFoundException("No student with such id:" + id);
        }
    }


    private Student findStudentByExternalId(UUID studentId) {
        return repository.findStudentByExternalId(studentId);
    }

    private StudentBasicInfoDTO mapToStudentBasicInfoDTO(Student foundStudent) {
        return StudentMapper.INSTANCE.studentToStudentBasicInfoDTO(foundStudent);
    }

    private boolean isStudentWithGivenPeselDoesNotExists(StudentToRegisterDTO studentToRegister) {
        return isNull(repository.findStudentByPesel(studentToRegister.getPesel()));
    }

    private Student save(Student student) {
        return repository.save(student);
    }

    private Student createStudent(StudentToRegisterDTO studentRegistration) {
        Student student = new Student();
        student.setBirthDate(parseBirthDate(studentRegistration));
        student.setDyslexic(Boolean.parseBoolean(studentRegistration.getDyslexic()));
        student.setFirstName(studentRegistration.getFirstName());
        student.setLastName(studentRegistration.getLastName());
        student.setPesel(studentRegistration.getPesel());
        student.setSchoolYear(SchoolYear.fromNumberRepresentation(Integer.valueOf(studentRegistration.getSchoolYear())));
        Address address = createAddress(studentRegistration);
        student.setAddress(address);
        return student;
    }

    private LocalDate parseBirthDate(StudentToRegisterDTO studentRegistration) {
        String birthDate = studentRegistration.getBirthDate();
        if (isNull(birthDate)) {
            throw new IllegalArgumentException("Birth date cannot be null.");
        }
        return LocalDate.parse(birthDate);
    }

    private Address createAddress(StudentToRegisterDTO studentRegistration) {
        Address address = new Address();
        address.setBuildingNumber(studentRegistration.getBuildingNumber());
        address.setApartmentNumber(studentRegistration.getApartmentNumber());
        address.setCity(studentRegistration.getCity());
        address.setPostalCode(studentRegistration.getPostalCode());
        address.setStreet(studentRegistration.getStreet());
        return address;
    }
}
