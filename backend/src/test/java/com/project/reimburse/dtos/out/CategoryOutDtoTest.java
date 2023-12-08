package com.project.reimburse.dtos.out;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class CategoryOutDtoTest {
	@Mock
    private CategoryOutDto categoryOutDto;

    @BeforeEach
    public void setUp() {
        categoryOutDto = new CategoryOutDto();
    }

    @Test
    public void testDefaultConstructor() {
        assertNotNull(categoryOutDto);
    }

    @Test
    public void testParameterizedConstructor() {
        String categoryName = "Category A";
        String categoryPriceLimit = "1000";

        categoryOutDto = new CategoryOutDto(categoryName, categoryPriceLimit);

        assertEquals(categoryName, categoryOutDto.getCategoryName());
        assertEquals(categoryPriceLimit, categoryOutDto.getCategoryPriceLimit());
    }

    @Test
    public void testSetAndGetters() {
        String categoryName = "Category B";
        String categoryPriceLimit = "500";

        categoryOutDto.setCategoryName(categoryName);
        categoryOutDto.setCategoryPriceLimit(categoryPriceLimit);

        assertEquals(categoryName, categoryOutDto.getCategoryName());
        assertEquals(categoryPriceLimit, categoryOutDto.getCategoryPriceLimit());
    }
    
    public void testToString() {
        CategoryOutDto dto1 = new CategoryOutDto("Travel", "1000");

        String expectedToString = "CategoryOutDto [categoryName=Travel, categoryPriceLimit=1000]";
        assertEquals(expectedToString, dto1.toString());
    }

    @Test
    public void testHashCode() {
        CategoryOutDto dto1 = new CategoryOutDto("Travel", "1000");
        CategoryOutDto dto2 = new CategoryOutDto("Food", "500");

        int hashCode1 = dto1.hashCode();
        int hashCode2 = dto2.hashCode();

        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    public void testEquals() {
        CategoryOutDto dto1 = new CategoryOutDto("Travel", "1000");
        CategoryOutDto dto1Clone = new CategoryOutDto("Travel", "1000");
        CategoryOutDto dto1DifferentCategory = new CategoryOutDto("Food", "1000");
        CategoryOutDto dto1DifferentPriceLimit = new CategoryOutDto("Travel", "500");

        assertTrue(dto1.equals(dto1));
        assertTrue(dto1.equals(dto1Clone));
        assertFalse(dto1.equals(null));
        assertFalse(dto1.equals("Official"));
        assertFalse(dto1.equals(dto1DifferentCategory));
        assertFalse(dto1.equals(dto1DifferentPriceLimit));
    }
    
}
