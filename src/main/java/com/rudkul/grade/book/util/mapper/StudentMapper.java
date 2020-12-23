package com.rudkul.grade.book.util.mapper;

import com.rudkul.grade.book.dto.StudentBasicInfoDTO;
import com.rudkul.grade.book.dto.StudentFullInfoDTO;
import com.rudkul.grade.book.entity.SchoolYear;
import com.rudkul.grade.book.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Mapper for student.
 */
@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentBasicInfoDTO studentToStudentBasicInfoDTO(Student student);

    @Mapping(source = "address.street", target = "street")
    @Mapping(source = "address.buildingNumber", target = "buildingNumber")
    @Mapping(source = "address.apartmentNumber", target = "apartmentNumber")
    @Mapping(source = "address.postalCode", target = "postalCode")
    @Mapping(source = "address.city", target = "city")
    StudentFullInfoDTO studentToStudentFullInfoDTO(Student student);

    default Integer schoolYearEnumToSchoolYear(SchoolYear schoolYear){
        return schoolYear.getNumberRepresentation();
    }
}
