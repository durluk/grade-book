package com.rudkul.grade.book.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Student registration information.
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StudentToRegisterDTO {
    private String firstName;
    private String lastName;
    private String schoolYear;
    private String pesel;
    private String birthDate;
    private String dyslexic;
    //student address
    private String street;
    private String buildingNumber;
    private String apartmentNumber;
    private String postalCode;
    private String city;
}
