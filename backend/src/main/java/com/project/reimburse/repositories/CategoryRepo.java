package com.project.reimburse.repositories;

import com.project.reimburse.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This is a class for Category Repo.
 */
public interface CategoryRepo extends JpaRepository<Category, Integer> {
  /**
   * This jpa method is used to find category by Category name.
   *
   * @param name Name of the category.
   * @return an object of Category.
 */
  Category findByCategoryName(String name);

  /**
   * This method is used to check whether the category is exists or not.
   *
 * @param categoryName Name of the category to check.
 * @return boolean value.
 */
  Boolean existsByCategoryName(String categoryName);
}
