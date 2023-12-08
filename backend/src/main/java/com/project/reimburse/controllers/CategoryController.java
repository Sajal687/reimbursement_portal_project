package com.project.reimburse.controllers;

import com.project.reimburse.dtos.in.CategoryInDto;
import com.project.reimburse.dtos.out.CategoryOutDto;
import com.project.reimburse.exceptions.ApiResponseMessage;
import com.project.reimburse.services.CategoryService;
import com.project.reimburse.utils.ApiHomePath;
import com.project.reimburse.utils.MessageConstants;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a Controller for Category.
 */
@CrossOrigin
@RestController
@RequestMapping(ApiHomePath.CATEGORY_HOME_PATH)
public class CategoryController {
  /**
  * Category Service Instance.
  */
  @Autowired
  private CategoryService categoryService;

  /**
   * This is a Logger Object.
   */
  private final Logger log = LoggerFactory
         .getLogger(CategoryController.class);


  /**
   * This Controller is used to create a category.
   *
   * @param category This Object consist of Category.
   * @return This returns CategoryOutDto object.
   */
  @PostMapping("/category")
  public ResponseEntity<CategoryOutDto> createCategory(
      final @Valid @RequestBody CategoryInDto category) {
    final CategoryOutDto resp = this.categoryService.createCategory(category);
    log.info("Category created successfully");
    return new ResponseEntity<CategoryOutDto>(resp, HttpStatus.CREATED);
  }

  /**
  * This Controller is used to get all the categories.
  *
  * @return This method return all the Categories.
  */
  @GetMapping("/categories")
  public ResponseEntity<List<CategoryOutDto>> getAllCategories() {
    log.info("Fetching all categories");
    final List<CategoryOutDto> resp = this.categoryService.getAllCategories();
    log.info("Fetched all categories");
    return new ResponseEntity<List<CategoryOutDto>>(resp, HttpStatus.OK);
  }

  /**
  * This Controller is used to delete the Category with the help of Category name.
  *
  * @param categoryName This argument is a name of the Category which we want to delete.
  * @return It returns an ApiResponse containing message and its status.
  */
  @DeleteMapping("/category/{categoryName}")
  public ResponseEntity<ApiResponseMessage> deleteCategoryByName(
      final @PathVariable String categoryName) {
    log.info("Deleting {}", categoryName);
    this.categoryService.deleteCategoryByName(categoryName);
    log.info("{} is deleted successfully.", categoryName);
    return new ResponseEntity<ApiResponseMessage>(new ApiResponseMessage(
        MessageConstants.CATEGORY_DELETED_MESSAGE, true), HttpStatus.OK);
  }
}
