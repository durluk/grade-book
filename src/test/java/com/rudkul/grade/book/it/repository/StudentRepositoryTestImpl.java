package com.rudkul.grade.book.it.repository;

import com.rudkul.grade.book.dataaccess.dao.StudentRepository;
import com.rudkul.grade.book.entity.Student;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

//Logic is implemented to only those method which are used in integration tests
public class StudentRepositoryTestImpl  implements StudentRepository {

    private final Map<Long, Student> idToStudent = new HashMap<>();
    private Long nextAvailableId = 1L;

    @Override
    public Student findStudentByExternalId(UUID externalId) {
        return null;
    }

    @Override
    public Student findStudentByPesel(String pesel) {
        return null;
    }

    @Override
    public <S extends Student> S save(S s) {
        s.setId(nextAvailableId);
        nextAvailableId++;
        idToStudent.put(s.getId(), s);
        return s;
    }

    @Override
    public <S extends Student> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Student> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Student> findAll() {
        return idToStudent.values();
    }

    @Override
    public Iterable<Student> findAllById(Iterable<Long> iterable) {
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
    public void delete(Student student) {

    }

    @Override
    public void deleteAll(Iterable<? extends Student> iterable) {
        idToStudent.clear();
    }

    @Override
    public void deleteAll() {

    }
}
