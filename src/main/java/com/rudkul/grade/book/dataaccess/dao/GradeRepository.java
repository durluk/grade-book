package com.rudkul.grade.book.dataaccess.dao;

import com.rudkul.grade.book.entity.Grade;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data access layer for Grade.
 */
@Repository
public interface GradeRepository extends CrudRepository<Grade, Long> {

    /**
     * Will return every grade which refers to student with given id.
     *
     * @param id technical id of student grades are searched
     * @return all grades referring to particular student
     */
    List<Grade> findGradeByStudentId(Long id);
}
