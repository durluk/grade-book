package com.rudkul.grade.book.rest;

import com.rudkul.grade.book.business.api.StudentService;
import com.rudkul.grade.book.dto.GradeToRegisterDTO;
import com.rudkul.grade.book.dto.StudentBasicInfoDTO;
import com.rudkul.grade.book.dto.StudentFullInfoDTO;
import com.rudkul.grade.book.dto.StudentToRegisterDTO;
import com.rudkul.grade.book.dto.StudentWithGradesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Endpoint for managing students and their grades.
 */
@RestController
@RequestMapping("/api/v1")
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public void createStudents(@RequestBody List<StudentToRegisterDTO> studentToRegisterDTOList) {
        studentService.registerStudents(studentToRegisterDTOList);
    }

    @GetMapping("/students")
    public List<StudentBasicInfoDTO> getAllStudents() {
        return studentService.getAll();
    }

    @GetMapping("/{id}/student")
    public StudentFullInfoDTO getStudent(@PathVariable("id") String id) {
        UUID studentId = UUID.fromString(id);
        return studentService.get(studentId);
    }

    @GetMapping("/{id}/student/grades")
    public StudentWithGradesDTO getStudentWithGrades(@PathVariable("id") String id) {
        UUID studentId = UUID.fromString(id);
        return studentService.getStudentGrades(studentId);
    }

    @PostMapping("/{id}/student/grades")
    @ResponseStatus(HttpStatus.CREATED)
    public void createGrades(@PathVariable("id") String id, @RequestBody List<GradeToRegisterDTO> gradesToRegister) {
        UUID studentId = UUID.fromString(id);
        studentService.giveGrade(studentId, gradesToRegister);
    }

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
}
