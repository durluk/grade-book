package com.rudkul.grade.book.business.impl;

import com.rudkul.grade.book.business.api.GradeService;
import com.rudkul.grade.book.dataaccess.dao.GradeRepository;
import com.rudkul.grade.book.dto.SubjectGradesDTO;
import com.rudkul.grade.book.entity.Grade;
import com.rudkul.grade.book.entity.SchoolSubject;
import com.rudkul.grade.book.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class GradeServiceImpl implements GradeService {

    private final GradeRepository repository;

    @Autowired
    public GradeServiceImpl(GradeRepository gradeRepository) {
        this.repository = gradeRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SubjectGradesDTO> getGradesForGivenStudent(Long studentId) {
        return repository.findGradeByStudentId(studentId)
                .stream()
                .collect(createGradeCollector())
                .entrySet()
                .stream()
                .map(this::createSubjectGradesDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerGrade(Student student, SchoolSubject schoolSubject, String gradeValue) {
        Grade grade = createGrade(student, schoolSubject, gradeValue);
        repository.save(grade);
    }

    private Grade createGrade(Student student, SchoolSubject schoolSubject, String gradeValue) {
        Grade grade = new Grade();
        grade.setSchoolSubject(schoolSubject);
        grade.setStudent(student);
        grade.setSchoolYear(student.getSchoolYear());
        grade.setValue(gradeValue);
        return grade;
    }

    private SubjectGradesDTO createSubjectGradesDTO(Map.Entry<String, List<String>> subjectNameToGrades) {
        return new SubjectGradesDTO(subjectNameToGrades.getKey(), subjectNameToGrades.getValue());
    }

    private Collector<Grade, ?, Map<String, List<String>>> createGradeCollector() {
        return Collectors.groupingBy(grade -> grade.getSchoolSubject().getName(),
                Collectors.mapping(Grade::getValue, Collectors.toList()));
    }
}
