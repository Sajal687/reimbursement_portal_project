package com.project.reimburse.dtos.out;

import java.util.Objects;

/**
 * This is a OutDto file of Category.
 */
public class CategoryOutDto {
  /**
   *This is a Parameterized Constructor of CategoryOutDto.
   *
   * @param str1 Name of the Category.
   * @param str2 Expense Limit.
   */
  public CategoryOutDto(final String str1, final String str2) {
    super();
    this.categoryName = str1;
    this.categoryPriceLimit = str2;
  }

  /**
   * Default Constructor of an Category.
   */
  public CategoryOutDto() {
    // TODO Auto-generated constructor stub
  }

  private String categoryName;

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
  * @param val Category Name to set.
  */
  public void setCategoryName(final String val) {
    this.categoryName = val;
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
  * @param val CategoryPriceLimit to set.
  */
  public void setCategoryPriceLimit(final String val) {
    this.categoryPriceLimit = val;
  }

  /**
 *This is a hashcode method.
 */
  @Override
  public int hashCode() {
    return Objects.hash(categoryName, categoryPriceLimit);
  }

  /**
 *This is a Equals method.
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
    CategoryOutDto other = (CategoryOutDto) obj;
    return Objects.equals(categoryName, other.categoryName)
       && Objects.equals(categoryPriceLimit, other.categoryPriceLimit);
  }

  /**
 *This is a toString method.
 */
  @Override
  public String toString() {
    return "CategoryOutDto [categoryName="
      + categoryName + ", categoryPriceLimit=" + categoryPriceLimit + "]";
  }
}
