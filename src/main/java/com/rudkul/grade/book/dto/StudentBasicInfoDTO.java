package com.rudkul.grade.book.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 * Student version with minimal information.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentBasicInfoDTO {
    private String firstName;
    private String lastName;
    private UUID externalId;
    private Integer schoolYear;
    private Boolean dyslexic;
}
