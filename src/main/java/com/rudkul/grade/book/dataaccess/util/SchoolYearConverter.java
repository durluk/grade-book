package com.rudkul.grade.book.dataaccess.util;

import com.rudkul.grade.book.entity.SchoolYear;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import static java.util.Objects.nonNull;

@Converter(autoApply = true)
public class SchoolYearConverter implements AttributeConverter<SchoolYear, Integer> {
    @Override
    public Integer convertToDatabaseColumn(SchoolYear schoolYear) {
        return nonNull(schoolYear) ? schoolYear.getNumberRepresentation() : null;
    }

    @Override
    public SchoolYear convertToEntityAttribute(Integer integer) {
        return SchoolYear.fromNumberRepresentation(integer);
    }
}
