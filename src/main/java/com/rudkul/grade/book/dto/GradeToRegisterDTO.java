package com.rudkul.grade.book.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * All info required to store grade information.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class GradeToRegisterDTO {
    private String subject;
    private String grade;
}
