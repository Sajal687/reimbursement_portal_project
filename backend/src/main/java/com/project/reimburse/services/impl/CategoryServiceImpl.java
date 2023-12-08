package com.project.reimburse.services.impl;

import com.project.reimburse.dtos.in.CategoryInDto;
import com.project.reimburse.dtos.out.CategoryOutDto;
import com.project.reimburse.entities.Category;
import com.project.reimburse.exceptions.BadRequestException;
import com.project.reimburse.exceptions.ResourceAlreadyExist;
import com.project.reimburse.repositories.CategoryRepo;
import com.project.reimburse.services.CategoryService;
import com.project.reimburse.utils.MessageConstants;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is a Implementation Class for Category Service.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
  /**
 * CategoryRepo Instance.
 */
  @Autowired
  private CategoryRepo categoryRepo;

  /**
   * This is a Logger Object.
   */
  private final Logger log = LoggerFactory
         .getLogger(CategoryServiceImpl.class);


  /**
   * This Service is used to create a category.
   *
   * @param categoryInDto This Object consist of Category.
   * @return This returns CategoryOutDto object.
   */
  @Override
  public CategoryOutDto createCategory(final CategoryInDto categoryInDto) {
    categoryInDto.setCategoryName(categoryInDto.getCategoryName().toUpperCase(Locale.ENGLISH));
    Boolean ifCategoryExists = this.categoryRepo.existsByCategoryName(
        categoryInDto.getCategoryName());
    if (ifCategoryExists) {
      throw new ResourceAlreadyExist(MessageConstants.CATEGORY_ALREADY_EXISTS);
    }
    log.info("Creating a category with category name {}", categoryInDto.getCategoryName());
    Category newCategory = categoryInDtoToCategory(categoryInDto);
    Category respCategory = this.categoryRepo.save(newCategory);
    return categoryToOutDto(respCategory);
  }

  /**
   * This Service implementation is used to get all the categories.
   *
   * @return This method return all the Categories.
   */
  @Override
  public List<CategoryOutDto> getAllCategories() {
    List<Category> allCategories = this.categoryRepo.findAll();
    log.info("Getting all Categories");
    List<CategoryOutDto> resp = allCategories.stream().map(
        (category) -> categoryToOutDto(category)).collect(Collectors.toList());
    return resp;
  }


  /**
   * This Service implementation is used to delete the Category with the help of Category name.
   *
   * @param categoryName This argument is a name of the Category which we want to delete.
   */
  @Override
  public void deleteCategoryByName(final String categoryName) {
    Category category = this.categoryRepo.findByCategoryName(categoryName);
    if (Objects.isNull(category)) {
      throw new BadRequestException(MessageConstants.CATEGORY_DOESNT_EXIST);
    }
    log.info("Deleting category with category name {}", categoryName);
    this.categoryRepo.deleteById(category.getId());
  }

  /**
   * This is a Custom Mapper for Category to CategoryInDto.
   *
 * @param categoryInDto Takes CategoryInDto Object instance.
 * @return category object.
 */
  public Category categoryInDtoToCategory(final CategoryInDto categoryInDto) {
    Category category = new Category();
    category.setCategoryName(categoryInDto.getCategoryName());
    category.setCategoryPriceLimit(categoryInDto.getCategoryPriceLimit());
    return category;
  }

  /**
   * This is a Custom Mapper for Category to CategoryOutDto.
   *
 * @param category Takes Category Object instance.
 * @return categoryOutDto object.
 */
  public CategoryOutDto categoryToOutDto(final Category category) {
    CategoryOutDto categoryOutDto = new CategoryOutDto();
    categoryOutDto.setCategoryName(category.getCategoryName());
    categoryOutDto.setCategoryPriceLimit(category.getCategoryPriceLimit());
    return categoryOutDto;
  }
}
