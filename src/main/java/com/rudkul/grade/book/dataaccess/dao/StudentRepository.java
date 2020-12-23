package com.rudkul.grade.book.dataaccess.dao;

import com.rudkul.grade.book.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Data access layer for Student.
 */
@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    /**
     * Returns student with given uuid if exists.
     *
     * @param externalId external id of searched student
     * @return found student
     */
    Student findStudentByExternalId(UUID externalId);

    /**
     * Returns student with given pesel if exists.
     *
     * @param pesel pesel of searched student.
     * @return found student
     */
    Student findStudentByPesel(String pesel);
}
