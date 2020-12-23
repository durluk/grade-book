package com.rudkul.grade.book.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "student")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "external_id", nullable = false, length = 36, unique = true)
    private UUID externalId;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 150)
    private String lastName;

    @Column(name = "school_year", nullable = false, length = 150)
    private SchoolYear schoolYear;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id", nullable = false)
    private Address address;

    @Column(name = "pesel", nullable = false, length = 11, unique = true)
    private String pesel;

    @Column(name = "birth_date", nullable = false, length = 11)
    private LocalDate birthDate;

    @Column(name = "dyslexic", nullable = false, length = 1)
    private Boolean dyslexic;

    @PrePersist
    public void assignExternalId() {
        this.externalId = UUID.randomUUID();
    }

}
