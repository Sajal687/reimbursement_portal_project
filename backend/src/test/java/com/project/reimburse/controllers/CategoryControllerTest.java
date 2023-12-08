package com.project.reimburse.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reimburse.controllers.CategoryController;
import com.project.reimburse.dtos.in.CategoryInDto;
import com.project.reimburse.dtos.out.CategoryOutDto;
import com.project.reimburse.exceptions.ApiResponseMessage;
import com.project.reimburse.services.CategoryService;
import com.project.reimburse.utils.MessageConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.junit.jupiter.api.Assertions.*;


@WebMvcTest(controllers = CategoryController.class)
@ExtendWith(MockitoExtension.class)
public class CategoryControllerTest {

    @InjectMocks
    private CategoryController categoryController;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
	private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
    	MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() throws Exception {
        CategoryInDto newCategory = new CategoryInDto("Travel", "1000");
        CategoryOutDto createdCategory = new CategoryOutDto("Travel", "1000");

        when(categoryService.createCategory(newCategory)).thenReturn(createdCategory);

        mockMvc.perform(post("/api/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCategory)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categoryPriceLimit").value("1000"));

        verify(categoryService, times(1)).createCategory(newCategory);
    }


    @Test
    public void testGetAllCategories() throws Exception {
        List<CategoryOutDto> categories = new ArrayList<>(Arrays.asList(
                new CategoryOutDto("HR", "1000"),
                new CategoryOutDto("IT", "1500")
        ));

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].categoryName").value("HR"))
                .andExpect(jsonPath("$[0].categoryPriceLimit").value("1000"))
                .andExpect(jsonPath("$[1].categoryName").value("IT"))
                .andExpect(jsonPath("$[1].categoryPriceLimit").value("1500"));

        verify(categoryService, times(1)).getAllCategories();
    }

    @Test
    public void testDeleteCategoryByName() throws Exception {
        doNothing().when(categoryService).deleteCategoryByName("HR");

        mockMvc.perform(delete("/api/category/HR")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(MessageConstants.CATEGORY_DELETED_MESSAGE));

        verify(categoryService, times(1)).deleteCategoryByName("HR");
    }
}
