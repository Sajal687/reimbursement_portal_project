package com.project.reimburse.controllers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reimburse.dtos.in.DepartmentInDtoClass;
import com.project.reimburse.dtos.out.DepartmentOutDtoClass;
import com.project.reimburse.services.DepartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = DepartmentController.class)
public class DepartmentControllerTests {

    @InjectMocks
    private DepartmentController departmentController;

    @MockBean
    private DepartmentService departmentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateDepartment() throws Exception {
        DepartmentInDtoClass newDepartment = new DepartmentInDtoClass(0, "HR");
        DepartmentOutDtoClass fetchDepartment = new DepartmentOutDtoClass(0, "HR");

        when(departmentService.createDepartment(newDepartment)).thenReturn(fetchDepartment);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/department")
                .content(objectMapper.writeValueAsString(newDepartment))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        verify(departmentService, times(1)).createDepartment(newDepartment);
    }

    @Test
    public void testGetDepartmentById() throws Exception {
        DepartmentOutDtoClass fetchDepartment = new DepartmentOutDtoClass(0, "HR");

        when(departmentService.getDepartmentById(0)).thenReturn(fetchDepartment);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/department/{id}", 0)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        DepartmentOutDtoClass responseDepartment = objectMapper.readValue(responseContent, DepartmentOutDtoClass.class);

        verify(departmentService, times(1)).getDepartmentById(0);
        assertEquals(fetchDepartment, responseDepartment);
    }

    @Test
    public void testGetAllDepartments() throws Exception {
        List<DepartmentOutDtoClass> fetchAllUsers = new ArrayList<>(Arrays.asList(
                new DepartmentOutDtoClass(0, "HR"),
                new DepartmentOutDtoClass(0, "IT"),
                new DepartmentOutDtoClass(0, "Management")));

        when(departmentService.getAllDepartments()).thenReturn(fetchAllUsers);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .get("/api/departments")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = mvcResult.getResponse().getContentAsString();
        List<DepartmentOutDtoClass> responseDepartments = objectMapper.readValue(responseContent, new TypeReference<List<DepartmentOutDtoClass>>() {});

        verify(departmentService, times(1)).getAllDepartments();
        assertEquals(fetchAllUsers, responseDepartments);
    }
}

