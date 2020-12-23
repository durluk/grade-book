package com.rudkul.grade.book.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rudkul.grade.book.business.api.StudentService;
import com.rudkul.grade.book.dto.GradeToRegisterDTO;
import com.rudkul.grade.book.dto.StudentToRegisterDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllStudents_Request_ReturnEverythingThereIs() throws Exception {
        // given when then
        mockMvc.perform(get("/api/v1/students"))
                .andExpect(status().isOk());
        verify(studentService).getAll();
    }

    @Test
    void createStudents_RequestWithBody_OrderCreation() throws Exception {
        // given
        List<StudentToRegisterDTO> studentToRegisterDTOList = new ArrayList<>();
        studentToRegisterDTOList.add(new StudentToRegisterDTO());

        // when then
        mockMvc.perform(post("/api/v1/students")
                .content(objectMapper.writeValueAsString(studentToRegisterDTOList))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(studentService).registerStudents(anyList());
    }

    @Test
    void getStudent_Request_ReturnSpecificStudentWithGivenId() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        // when then
        mockMvc.perform(get("/api/v1/" + uuid.toString() + "/student"))
                .andExpect(status().isOk());
        verify(studentService).get(eq(uuid));
    }

    @Test
    void getStudentWithGrades_Request_ReturnSpecificStudentWithGivenIdAndGrades() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        // when then
        mockMvc.perform(get("/api/v1/" + uuid.toString() + "/student/grades"))
                .andExpect(status().isOk());
        verify(studentService).getStudentGrades(eq(uuid));
    }

    @Test
    void createGrades_Request_RegisterGradesForStudentWithGivenId() throws Exception {
        // given
        UUID uuid = UUID.randomUUID();
        List<GradeToRegisterDTO> gradesToRegister = Collections.singletonList(new GradeToRegisterDTO());
        // when then
        mockMvc.perform(post("/api/v1/" + uuid.toString() + "/student/grades")
                .content(objectMapper.writeValueAsString(gradesToRegister))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(studentService).giveGrade(eq(uuid), anyList());
    }
}