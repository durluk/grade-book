package com.rudkul.grade.book.it.repository;

import com.rudkul.grade.book.dataaccess.dao.GradeRepository;
import com.rudkul.grade.book.entity.Grade;

import java.util.List;
import java.util.Optional;

//Logic is implemented to only those method which are used in integration tests
public class GradeRepositoryTestImpl implements GradeRepository {
    @Override
    public List<Grade> findGradeByStudentId(Long id) {
        return null;
    }

    @Override
    public <S extends Grade> S save(S s) {
        return null;
    }

    @Override
    public <S extends Grade> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Grade> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Grade> findAll() {
        return null;
    }

    @Override
    public Iterable<Grade> findAllById(Iterable<Long> iterable) {
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
    public void delete(Grade grade) {

    }

    @Override
    public void deleteAll(Iterable<? extends Grade> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
