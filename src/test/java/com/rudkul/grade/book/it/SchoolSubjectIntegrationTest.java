package com.rudkul.grade.book.it;

import com.rudkul.grade.book.business.api.SchoolSubjectService;
import com.rudkul.grade.book.business.impl.SchoolSubjectServiceImpl;
import com.rudkul.grade.book.dataaccess.dao.SchoolSubjectRepository;
import com.rudkul.grade.book.dto.SchoolSubjectDTO;
import com.rudkul.grade.book.entity.SchoolSubject;
import com.rudkul.grade.book.it.repository.SchoolSubjectRepositoryTestImpl;
import com.rudkul.grade.book.rest.SchoolSubjectController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.util.CollectionUtils.isEmpty;

public class SchoolSubjectIntegrationTest {

    private final SchoolSubjectRepository schoolSubjectRepository = new SchoolSubjectRepositoryTestImpl();
    private final SchoolSubjectService schoolSubjectService = new SchoolSubjectServiceImpl(schoolSubjectRepository);
    private final SchoolSubjectController schoolSubjectController = new SchoolSubjectController(schoolSubjectService);


    @BeforeEach
    public void setUp() {
        schoolSubjectRepository.deleteAll();
    }

    @Test
    public void getAllSchoolSubjects_ShouldReturnAllSubject() {
        // given
        createAndSaveSchoolSubject("Biologia");
        createAndSaveSchoolSubject("Historia");

        // when
        List<SchoolSubjectDTO> allSchoolSubjects = schoolSubjectController.getAllSchoolSubjects();

        // then
        assertFalse(isEmpty(allSchoolSubjects));
        assertEquals(2, allSchoolSubjects.size());

    }

    @Test
    public void createSchoolSubjects_ShouldCreateAndSaveAllGivenSubjects() {
        // given
        List<String> subjectNames = asList("Biologia", "Historia");

        // when
        schoolSubjectController.createSchoolSubjects(subjectNames);

        // then
        Iterable<SchoolSubject> persisted = schoolSubjectRepository.findAll();
        persisted.forEach(schoolSubject -> assertTrue(subjectNames.contains(schoolSubject.getName())));
    }

    private void createAndSaveSchoolSubject(String name) {
        SchoolSubject schoolSubject = new SchoolSubject();
        schoolSubject.setName(name);
        schoolSubjectRepository.save(schoolSubject);
    }
}
