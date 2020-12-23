package com.rudkul.grade.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Information with subject and grades from subject.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class SubjectGradesDTO {

    private String subjectName;
    private List<String> grades;
}
