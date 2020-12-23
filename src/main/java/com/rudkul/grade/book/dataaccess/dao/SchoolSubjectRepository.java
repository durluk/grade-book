package com.rudkul.grade.book.dataaccess.dao;

import com.rudkul.grade.book.entity.SchoolSubject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data access layer for SchoolSubject.
 */
@Repository
public interface SchoolSubjectRepository extends CrudRepository<SchoolSubject, Long> {

    /**
     * Return school subject with given name if exists.
     *
     * @param name name of searched school subject
     * @return found school subject
     */
    SchoolSubject findSchoolSubjectByName(String name);
}
