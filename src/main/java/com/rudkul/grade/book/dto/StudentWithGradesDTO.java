package com.rudkul.grade.book.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * Student minimal information with all own grades.
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class StudentWithGradesDTO {
    private StudentBasicInfoDTO studentInfo;
    private List<SubjectGradesDTO> grades;
}
