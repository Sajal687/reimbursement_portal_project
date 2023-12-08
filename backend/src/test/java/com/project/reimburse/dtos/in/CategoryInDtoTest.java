package com.project.reimburse.dtos.in;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class CategoryInDtoTest {
	@Mock
    private CategoryInDto categoryInDto;

    @BeforeEach
    public void setUp() {
        categoryInDto = new CategoryInDto();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(categoryInDto);
    }

    @Test
    public void testParameterizedConstructor() {
        String categoryName = "Travel";
        String categoryPriceLimit = "500.00";

        categoryInDto = new CategoryInDto(categoryName, categoryPriceLimit);

        assertEquals(categoryName, categoryInDto.getCategoryName());
        assertEquals(categoryPriceLimit, categoryInDto.getCategoryPriceLimit());
    }

    @Test
    public void testSetAndGetCategoryName() {
        String categoryName = "Food";
        categoryInDto.setCategoryName(categoryName);
        assertEquals(categoryName, categoryInDto.getCategoryName());
    }

    @Test
    public void testSetAndGetCategoryPriceLimit() {
        String categoryPriceLimit = "100.00";
        categoryInDto.setCategoryPriceLimit(categoryPriceLimit);
        assertEquals(categoryPriceLimit, categoryInDto.getCategoryPriceLimit());
    }
    
    @Test
    public void testToString() {
    	CategoryInDto dto1 = new CategoryInDto("Travel", "100");
        CategoryInDto dto2 = new CategoryInDto("Food", "200");

        String expectedToString = "CategoryInDto [categoryName=Travel, categoryPriceLimit=100]";
        assertEquals(expectedToString, dto1.toString());
    }

    @Test
    public void testHashCode() {
    	CategoryInDto dto1 = new CategoryInDto("Travel", "100");
        CategoryInDto dto2 = new CategoryInDto("Food", "200");
        
        int hashCode1 = dto1.hashCode();
        int hashCode2 = dto2.hashCode();

        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    public void testEquals() {
    	CategoryInDto dto1 = new CategoryInDto("Travel", "100");
        CategoryInDto dto2 = new CategoryInDto("Food", "200");
        
        CategoryInDto dto1Clone = new CategoryInDto("Travel", "100");
        CategoryInDto dto1DifferentName = new CategoryInDto("Official Supplies", "100");
        CategoryInDto dto1DifferentPrice = new CategoryInDto("Transport", "200");

        assertTrue(dto1.equals(dto1));
        assertTrue(dto1.equals(dto1Clone));
        assertFalse(dto1.equals(dto2));
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("Category1"));
        assertFalse(dto1.equals(dto1DifferentName));
        assertFalse(dto1.equals(dto1DifferentPrice));
    }
}
