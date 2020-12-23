package com.rudkul.grade.book.util.mapper;

import com.rudkul.grade.book.dto.SchoolSubjectDTO;
import com.rudkul.grade.book.entity.SchoolSubject;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for schoolSubject.
 */
@Mapper
public interface SchoolSubjectMapper {

    SchoolSubjectMapper INSTANCE = Mappers.getMapper(SchoolSubjectMapper.class);

    SchoolSubjectDTO schoolSubjectToSchoolSubjectDTO(SchoolSubject schoolSubject);
}
