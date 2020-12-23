package com.rudkul.grade.book.dataaccess.util;

import com.rudkul.grade.book.entity.SchoolYear;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


class SchoolYearConverterTest {

    private final SchoolYearConverter schoolYearConverter = new SchoolYearConverter();

    @ParameterizedTest
    @EnumSource(SchoolYear.class)
    void convertToDatabaseColumn_AllAvailableSchoolYears_ShouldMap(SchoolYear schoolYear) {
        // given
        Integer expectedSchoolYearNumberRepresentation = schoolYear.getNumberRepresentation();

        // when
        Integer result = schoolYearConverter.convertToDatabaseColumn(schoolYear);

        // then
        assertEquals(expectedSchoolYearNumberRepresentation, result);
    }

    @Test
    void convertToDatabaseColumn_NullInput_ShouldMapToNull() {
        // given when
        Integer result = schoolYearConverter.convertToDatabaseColumn(null);

        // then
        assertNull(result);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8})
    void convertToEntityAttribute_AllAvailableSchoolYears_ShouldMap(Integer schoolYearNumberRepresentation) {
        // given
        SchoolYear expectedSchoolYear = SchoolYear
                .fromNumberRepresentation(schoolYearNumberRepresentation);

        // when
        SchoolYear result = schoolYearConverter.convertToEntityAttribute(schoolYearNumberRepresentation);

        // then
        assertEquals(expectedSchoolYear, result);
    }

    @Test
    void convertToEntityAttribute_AAAAllAvailableSchoolYears_ShouldMap() {
        // given when then
        assertThrows(UnsupportedOperationException.class, () -> schoolYearConverter.convertToEntityAttribute(11));
    }
}