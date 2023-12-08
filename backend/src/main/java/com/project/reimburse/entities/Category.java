package com.project.reimburse.entities;


import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This is a Category Entity class.
 */
@Entity
@Table(name = "categories")
public class Category {
  /**
  * This is a Category Constructor.
  *
  * @param categoryId Actual Id of a database.
  * @param str1 Name of the Category.
  * @param str2 Price limit of the category.
  */
  public Category(final int categoryId, final String str1,
      final String str2) {
    super();
    this.id = categoryId;
    this.categoryName = str1;
    this.categoryPriceLimit = str2;
  }

  /**
   * Default Constructor of an Category.
  */
  public Category() {
  // TODO Auto-generated constructor stub
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "category_id")
  private int id;

  @Column(name = "category_name", nullable = false, unique = true)
  private String categoryName;

  @Column(name = "max_claim_amt", nullable = false)
  private String categoryPriceLimit;

  /**
   * Getter of an Id.
   *
   * @return id.
 */
  public int getId() {
    return id;
  }

  /**
   * Setter of an Id.
   *
 * @param categoryId ID to set.
 */
  public void setId(final int categoryId) {
    this.id = categoryId;
  }

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
 *This is a hashCode method.
 */
  @Override
  public int hashCode() {
    return Objects.hash(categoryName, categoryPriceLimit, id);
  }

  /**
 *This is a equals method of an Category entity.
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
    Category other = (Category) obj;
    return Objects.equals(categoryName, other.categoryName)
      && Objects.equals(categoryPriceLimit, other.categoryPriceLimit) && id == other.id;
  }

  /**
 *This is a toString of Category Entity.
 */
  @Override
  public String toString() {
    return "Category [id=" + id + ", categoryName=" + categoryName + ","
       + " categoryPriceLimit=" + categoryPriceLimit + "]";
  }
}
