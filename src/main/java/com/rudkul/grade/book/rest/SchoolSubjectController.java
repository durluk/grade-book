package com.rudkul.grade.book.rest;

import com.rudkul.grade.book.business.api.SchoolSubjectService;
import com.rudkul.grade.book.dto.SchoolSubjectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Endpoint for managing school subjects.
 */
@RestController
@RequestMapping("/api/v1")
public class SchoolSubjectController {

    private final SchoolSubjectService schoolSubjectService;

    @GetMapping("/school-subjects")
    public List<SchoolSubjectDTO> getAllSchoolSubjects() {
        return schoolSubjectService.getAll();
    }

    @PostMapping("/school-subjects")
    @ResponseStatus(HttpStatus.CREATED)
    public void createSchoolSubjects(@RequestBody List<String> schoolSubjectNames) {
        schoolSubjectService.registerSubjects(schoolSubjectNames);
    }

    @Autowired
    public SchoolSubjectController(SchoolSubjectService subjectSchoolService) {
        this.schoolSubjectService = subjectSchoolService;
    }
}
