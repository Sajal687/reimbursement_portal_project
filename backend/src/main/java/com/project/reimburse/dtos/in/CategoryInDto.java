package com.project.reimburse.dtos.in;


import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * This is CategoryInDto Class.
 */
public class CategoryInDto {
  /**
   *This is a Parameterized Constructor of CategoryInDto.
   *
 * @param str1 Name of the Category.
 * @param str2 Expense Limit.
 */
  public CategoryInDto(
      final @NotEmpty String str1, final @NotEmpty String str2) {
    super();
    this.categoryName = str1;
    this.categoryPriceLimit = str2;
  }

  /**
 * This is a Default Constructor.
 */
  public CategoryInDto() {
   // TODO Auto-generated constructor stub
  }

  @NotBlank(message = "Category name is required.")
  @Size(min = 2, message = "Category name must contains at least 2 characters.")
  @Pattern(message = "Category name must contain only alphabets.", regexp = "^[a-zA-Z_ ]*$")
  private String categoryName;

  @NotBlank(message = "Category price limit is required.")
  @Size(min = 3, message = "Category price limit must be greater than 99.")
  @Pattern(message = "Category price must only contains numbers.", regexp = "^[0-9]*$")
  private String categoryPriceLimit;

  /**
   * Getter of an Category Name.
   *
 * @return categoryName
 */
  public String getCategoryName() {
    return categoryName;
  }

  /**
   *Setter of an Category Name.
   *
 * @param str1 Category Name to set.
 */
  public void setCategoryName(final String str1) {
    this.categoryName = str1;
  }

  /**
   * Getter for a Price Limit.
   *
 * @return categoryPriceLimit.
 */
  public String getCategoryPriceLimit() {
    return categoryPriceLimit;
  }

  /**
   * Setter for a Price Limit.
   *
   * @param str1 CategoryPriceLimit to set.
  */
  public void setCategoryPriceLimit(final String str1) {
    this.categoryPriceLimit = str1;
  }

  /**
 *This is hashcode method.
 */
  @Override
  public int hashCode() {
    return Objects.hash(categoryName, categoryPriceLimit);
  }

  /**
 *This is equals method.
 */
  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    CategoryInDto other = (CategoryInDto) obj;
    return Objects.equals(categoryName, other.categoryName)
       && Objects.equals(categoryPriceLimit, other.categoryPriceLimit);
  }

  /**
 *This is a toString.
 */
  @Override
  public String toString() {
    return "CategoryInDto [categoryName="
      + categoryName + ", categoryPriceLimit=" + categoryPriceLimit + "]";
  }
}
