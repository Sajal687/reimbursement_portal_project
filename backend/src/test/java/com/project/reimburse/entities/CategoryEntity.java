package com.project.reimburse.entities;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CategoryEntity {

	@Mock
	private Category category;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCategoryConstructor() {
		int categoryId = 1;
		String categoryName = "CategoryName";
		String categoryPriceLimit = "1000";

		Category category = new Category(categoryId, categoryName, categoryPriceLimit);

		assertNotNull(category);
		assertEquals(categoryId, category.getId());
		assertEquals(categoryName, category.getCategoryName());
		assertEquals(categoryPriceLimit, category.getCategoryPriceLimit());
	}

	@Test
	public void testGettersAndSetters() {
		Category category = new Category();
		category.setId(1);
		category.setCategoryName("CategoryName");
		category.setCategoryPriceLimit("1000");

		assertEquals(1, category.getId());
		assertEquals("CategoryName", category.getCategoryName());
		assertEquals("1000", category.getCategoryPriceLimit());
	}

	@Test
    public void testToString() {
        int id = 1;
        String categoryName = "Travel";
        String categoryPriceLimit = "1000";

        Category category = new Category(id, categoryName, categoryPriceLimit);

        String expectedToString = "Category [id=" + id + ", categoryName=" + categoryName +
                ", categoryPriceLimit=" + categoryPriceLimit + "]";
        assertEquals(expectedToString, category.toString());
    }

    @Test
    public void testHashCode() {
        int id1 = 1;
        int id2 = 2;
        String categoryName1 = "Travel";
        String categoryName2 = "Food";
        String categoryPriceLimit1 = "1000";
        String categoryPriceLimit2 = "2000";

        Category category1 = new Category(id1, categoryName1, categoryPriceLimit1);
        Category category2 = new Category(id2, categoryName2, categoryPriceLimit2);
        Category category3 = new Category(1 , "Travel" , "1000");
        		

        int hashCode1 = category1.hashCode();
        int hashCode2 = category2.hashCode();
        int hashCode3 = category3.hashCode();
        
        assertEquals(hashCode1, hashCode3);
        assertNotEquals(hashCode1, hashCode2);
    }

    @Test
    public void testEquals() {
        int id = 1;
        String categoryName = "Travel";
        String categoryPriceLimit = "1000";

        Category category1 = new Category(id, categoryName, categoryPriceLimit);
        Category category2 = new Category(id, categoryName, categoryPriceLimit);
        Category category3 = new Category(2, categoryName, categoryPriceLimit);
        Category category4 = new Category(id, "DifferentCategory", categoryPriceLimit);

        assertTrue(category1.equals(category1));
        assertTrue(category1.equals(category2));
        assertFalse(category1.equals(null));
        assertFalse(category1.equals("Category"));
        assertFalse(category1.equals(category3));
        assertFalse(category1.equals(category4));
    }
}
