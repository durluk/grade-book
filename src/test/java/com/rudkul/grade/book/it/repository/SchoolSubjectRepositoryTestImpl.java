package com.rudkul.grade.book.it.repository;

import com.rudkul.grade.book.dataaccess.dao.SchoolSubjectRepository;
import com.rudkul.grade.book.entity.SchoolSubject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

//Logic is implemented to only those method which are used in integration tests
public class SchoolSubjectRepositoryTestImpl implements SchoolSubjectRepository {

    private final Map<Long, SchoolSubject> idToSchoolSubject = new HashMap<>();
    private Long nextAvailableId = 1L;


    @Override
    public SchoolSubject findSchoolSubjectByName(String name) {
        return null;
    }

    @Override
    public <S extends SchoolSubject> S save(S schoolSubject) {
        schoolSubject.setId(nextAvailableId);
        nextAvailableId++;
        idToSchoolSubject.put(schoolSubject.getId(), schoolSubject);
        return schoolSubject;
    }

    @Override
    public <S extends SchoolSubject> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<SchoolSubject> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<SchoolSubject> findAll() {
        return idToSchoolSubject.values();
    }

    @Override
    public Iterable<SchoolSubject> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(SchoolSubject schoolSubject) {

    }

    @Override
    public void deleteAll(Iterable<? extends SchoolSubject> iterable) {
        idToSchoolSubject.clear();
    }

    @Override
    public void deleteAll() {

    }
}