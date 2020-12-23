package com.rudkul.grade.book.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Student version with all information.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentFullInfoDTO extends StudentBasicInfoDTO {

    private String pesel;
    private LocalDate birthDate;

    //Address
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
    private String city;
}
