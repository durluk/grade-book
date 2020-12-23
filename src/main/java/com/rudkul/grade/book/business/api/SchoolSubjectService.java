package com.rudkul.grade.book.business.api;

import com.rudkul.grade.book.dto.SchoolSubjectDTO;
import com.rudkul.grade.book.entity.SchoolSubject;
import com.rudkul.grade.book.util.exception.NotFoundException;

import java.util.List;

/**
 * Service for school subjects
 */
public interface SchoolSubjectService {

    /**
     * Creates and saves school subjects from given subject names. Ignores duplicates.
     *
     * @param schoolSubjectNames list of names to create subjects of.
     */
    void registerSubjects(List<String> schoolSubjectNames);

    /**
     * Returns all persisted subjects.
     *
     * @return list of subjects
     */
    List<SchoolSubjectDTO> getAll();

    /**
     * Returns subject with given name. Throws {@link NotFoundException} if no
     * such subject exists.
     *
     * @param name searched name
     * @return subject with given name
     */
    SchoolSubject get(String name);
}
