package com.rudkul.grade.book.entity;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

//For polish schools maximal number of years(levels) is eight - elementary school
@Getter
public enum SchoolYear {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5),
    SIXTH(6),
    SEVENTH(7),
    EIGHTH(8);

    private static final Map<Integer, SchoolYear> SCHOOL_YEAR_TO_NUMBER_REPRESENTATION = Arrays
            .stream(SchoolYear.values())
            .collect(Collectors.toMap(SchoolYear::getNumberRepresentation, schoolYear -> schoolYear));

    private final Integer numberRepresentation;

    SchoolYear(Integer numberRepresentation) {
        this.numberRepresentation = numberRepresentation;
    }

    public static SchoolYear fromNumberRepresentation(Integer numberRepresentation) {
        if (isNumberRepresentationExisting(numberRepresentation)) {
            throw new UnsupportedOperationException("No school year with number representation:" + numberRepresentation);
        }
        return SCHOOL_YEAR_TO_NUMBER_REPRESENTATION.getOrDefault(numberRepresentation, null);
    }

    private static boolean isNumberRepresentationExisting(Integer numberRepresentation) {
        return !SCHOOL_YEAR_TO_NUMBER_REPRESENTATION.containsKey(numberRepresentation);
    }
}
