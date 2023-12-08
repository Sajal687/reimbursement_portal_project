package com.project.reimburse.services.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

import com.project.reimburse.dtos.in.CategoryInDto;
import com.project.reimburse.dtos.out.CategoryOutDto;
import com.project.reimburse.entities.Category;
import com.project.reimburse.exceptions.ResourceAlreadyExist;
import com.project.reimburse.exceptions.BadRequestException;
import com.project.reimburse.repositories.CategoryRepo;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCategory() {
        CategoryInDto categoryInDto = new CategoryInDto("Travel", "2000");
        Category newCategory = categoryService.categoryInDtoToCategory(categoryInDto);
        Category savedCategory = new Category(1, "Travel", "2000");
        
         when(categoryRepo.save(any(Category.class))).thenReturn(savedCategory);
         
        CategoryOutDto result = categoryService.createCategory(categoryInDto);
        
        assertThat(result.getCategoryName()).isEqualTo("Travel");
        assertThat(result.getCategoryPriceLimit()).isEqualTo("2000");
    }
    
    @Test
    public void testCreateCategory_CategoryAlreadyExists() {
        CategoryInDto categoryInDto = new CategoryInDto();
        categoryInDto.setCategoryName("Travel");

        when(categoryRepo.existsByCategoryName("TRAVEL")).thenReturn(true);

        assertThrows(ResourceAlreadyExist.class,() -> categoryService.createCategory(categoryInDto));
    }

    @Test
    public void testGetAllCategories() {
        Category category1 = new Category(1, "Travel", "2000");
        Category category2 = new Category(2, "Food", "4000");
        List<Category> categories = Arrays.asList(category1, category2);
        
        when(categoryRepo.findAll()).thenReturn(categories);
        
        List<CategoryOutDto> result = categoryService.getAllCategories();
        
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getCategoryName()).isEqualTo("Travel");
        assertThat(result.get(0).getCategoryPriceLimit()).isEqualTo("2000");
        assertThat(result.get(1).getCategoryName()).isEqualTo("Food");
        assertThat(result.get(1).getCategoryPriceLimit()).isEqualTo("4000");
    }

    @Test
    public void testDeleteCategoryByName() {
        String categoryName = "Travel";
        Category category = new Category(1, categoryName, "2000");
        
        when(categoryRepo.findByCategoryName(categoryName)).thenReturn(category);
     
        categoryService.deleteCategoryByName(categoryName);
        
        verify(categoryRepo, times(1)).deleteById(1);
    }
    
    @Test
    public void testDeleteCategoryByName_CategoryDoesNotExist() {
    	String categoryName = "Travel";
        when(categoryRepo.findByCategoryName(categoryName)).thenReturn(null);

        assertThrows(BadRequestException.class,() -> categoryService.deleteCategoryByName(categoryName));
    }
}

