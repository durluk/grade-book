package com.rudkul.grade.book.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rudkul.grade.book.business.api.SchoolSubjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SchoolSubjectController.class)
class SchoolSubjectControllerTest {

    @MockBean
    private SchoolSubjectService schoolSubjectService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllSchoolSubjects_Request_ReturnEverythingThereIs() throws Exception {
        // given when then
        mockMvc.perform(get("/api/v1/school-subjects"))
                .andExpect(status().isOk());
        verify(schoolSubjectService).getAll();
    }

    @Test
    void createSchoolSubjects_RequestWithBody_OrderCreation() throws Exception {
        // given
        List<String> request = asList("Biologia", "Geografia");

        // when then
        mockMvc.perform(post("/api/v1/school-subjects")
                .content(objectMapper.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        verify(schoolSubjectService).registerSubjects(eq(request));
    }
}