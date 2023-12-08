package com.project.reimburse.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.reimburse.entities.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CategoryRepoTests {
	@Autowired CategoryRepo categoryRepository;
	
	@Test
	public void testFindByCategoryName() {
		Category newCategory = new Category(1 , "Travel" , "2000");
		Category savedCategory = categoryRepository.save(newCategory);
		
		Category findCategoryByName = categoryRepository.findByCategoryName("Travel");
		assertThat(findCategoryByName.getCategoryName()).isEqualTo(savedCategory.getCategoryName());
		assertThat(findCategoryByName).isNotNull();
	}
	
	
			
}
