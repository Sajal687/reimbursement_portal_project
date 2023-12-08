package com.project.reimburse.services;

import com.project.reimburse.dtos.in.CategoryInDto;
import com.project.reimburse.dtos.out.CategoryOutDto;
import java.util.List;

/**
 * This is Category Service Interface.
 */
public interface CategoryService {
  /**
      * This Service is used to create a category.
      *
      * @param category This Object consist of Category.
      * @return This returns CategoryOutDto object.
   */
  CategoryOutDto createCategory(CategoryInDto category);

  /**
   * This Service is used to get all the categories.
   *
   * @return This method return all the Categories.
   */
  List<CategoryOutDto> getAllCategories();


  /**
   * This Controller is used to delete the Category with the help of Category name.
   *
   * @param categoryName This argument is a name of the Category which we want to delete.
   */
  void deleteCategoryByName(String categoryName);
}
