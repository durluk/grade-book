package com.rudkul.grade.book.business.impl;

import com.rudkul.grade.book.dataaccess.dao.SchoolSubjectRepository;
import com.rudkul.grade.book.dto.SchoolSubjectDTO;
import com.rudkul.grade.book.entity.SchoolSubject;
import com.rudkul.grade.book.util.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.util.CollectionUtils.isEmpty;

@ExtendWith({SpringExtension.class})
class SchoolSubjectServiceImplTest {

    @InjectMocks
    private SchoolSubjectServiceImpl schoolSubjectService;

    @Mock
    private SchoolSubjectRepository repository;

    @Test
    void registerSubjects_ForNotEmptyInput_ShouldCreateAndSaveSubject() {
        // given
        List<String> subjectNames = asList("Matematyka", "Informatyka");

        // when
        schoolSubjectService.registerSubjects(subjectNames);

        // then
        verify(repository, times(2)).findSchoolSubjectByName(anyString());
        verify(repository, times(2)).save(any(SchoolSubject.class));
    }

    @Test
    void registerSubjects_ForNameWhichAlreadyExists_ShouldNotCreateAndSaveSubject() {
        // given
        String subjectName = "Matematyka";
        List<String> subjectNames = singletonList(subjectName);
        when(repository.findSchoolSubjectByName(eq(subjectName))).thenReturn(new SchoolSubject());

        // when
        schoolSubjectService.registerSubjects(subjectNames);

        // then
        verify(repository).findSchoolSubjectByName(anyString());
        verify(repository, never()).save(any(SchoolSubject.class));
    }

    @Test
    void get_ForSpecificNameWhenExists_ShouldReturn() {
        // given
        String subjectName = "Matematyka";
        SchoolSubject expectedSchoolSubject = new SchoolSubject();
        when(repository.findSchoolSubjectByName(eq(subjectName))).thenReturn(expectedSchoolSubject);

        // when
        SchoolSubject schoolSubject = schoolSubjectService.get(subjectName);

        // then
        verify(repository).findSchoolSubjectByName(anyString());
        assertEquals(expectedSchoolSubject, schoolSubject);
    }

    @Test
    void get_ForSpecificNameWhenNotExists_ShouldThrowNotFoundException() {
        // given
        String subjectName = "Matematyka";
        when(repository.findSchoolSubjectByName(eq(subjectName))).thenReturn(null);

        // when then
        assertThrows(NotFoundException.class, () -> schoolSubjectService.get(subjectName));
        verify(repository).findSchoolSubjectByName(anyString());
    }

    @Test
    void getAll_SubjectsExistsInRepository_ShouldFetchAndMapToDTOs() {
        // given
        List<SchoolSubject> subjectNames = asList(new SchoolSubject(), new SchoolSubject());
        when(repository.findAll()).thenReturn(subjectNames);

        // when
        List<SchoolSubjectDTO> all = schoolSubjectService.getAll();

        // then
        assertFalse(isEmpty(all));
        assertEquals(2, all.size());
    }

    @Test
    void getAll_SubjectsNotExistsInRepository_ShouldFetchEmptyListAndMapToEmptyDTOList() {
        // given
        when(repository.findAll()).thenReturn(emptyList());

        // when
        List<SchoolSubjectDTO> all = schoolSubjectService.getAll();

        // then
        assertTrue(isEmpty(all));
    }
}