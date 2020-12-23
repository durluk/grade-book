package com.rudkul.grade.book.business.impl;

import com.rudkul.grade.book.business.api.SchoolSubjectService;
import com.rudkul.grade.book.dataaccess.dao.SchoolSubjectRepository;
import com.rudkul.grade.book.dto.SchoolSubjectDTO;
import com.rudkul.grade.book.entity.SchoolSubject;
import com.rudkul.grade.book.util.exception.NotFoundException;
import com.rudkul.grade.book.util.mapper.SchoolSubjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.util.Objects.isNull;

/**
 * {@inheritDoc}
 */
@Service
@Transactional
public class SchoolSubjectServiceImpl implements SchoolSubjectService {

    private final SchoolSubjectRepository repository;

    @Autowired
    public SchoolSubjectServiceImpl(@Qualifier("schoolSubjectRepository") SchoolSubjectRepository repository) {
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerSubjects(List<String> schoolSubjectNames) {
        schoolSubjectNames.forEach(this::saveNewSubject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SchoolSubjectDTO> getAll() {
        return StreamSupport
                .stream(repository.findAll().spliterator(), false)
                .map(SchoolSubjectMapper.INSTANCE::schoolSubjectToSchoolSubjectDTO)
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SchoolSubject get(String name) {
        SchoolSubject schoolSubjectByName = findSchoolSubjectByName(name);
        if (isNull(schoolSubjectByName)) {
            throw new NotFoundException("No school subject with such name:" + name);
        }
        return schoolSubjectByName;
    }

    private SchoolSubject findSchoolSubjectByName(String name) {
        return repository.findSchoolSubjectByName(name);
    }

    private void saveNewSubject(String schoolSubjectName) {
        if (isSubjectWithGivenNameDoesNotExists(schoolSubjectName)) {
            SchoolSubject schoolSubject = createSchoolSubject(schoolSubjectName);
            saveSchoolSubject(schoolSubject);
        } else {
            //TODO should log
        }
    }

    private void saveSchoolSubject(SchoolSubject schoolSubject) {
        repository.save(schoolSubject);
    }

    private SchoolSubject createSchoolSubject(String schoolSubjectName) {
        return new SchoolSubject(schoolSubjectName);
    }

    private boolean isSubjectWithGivenNameDoesNotExists(String schoolSubjectName) {
        return isNull(this.findSchoolSubjectByName(schoolSubjectName));
    }
}
