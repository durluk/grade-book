package com.rudkul.grade.book.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "street", nullable = false, length = 200)
    private String street;

    @Column(name = "building_number", nullable = false, length = 50)
    private String buildingNumber;

    @Column(name = "apartment_number", length = 5)
    private String apartmentNumber;

    @Column(name = "postal_code", nullable = false, length = 6)
    private String postalCode;

    @Column(name = "city", nullable = false, length = 200)
    private String city;

}
