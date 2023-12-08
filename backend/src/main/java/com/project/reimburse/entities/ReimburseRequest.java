package com.project.reimburse.entities;

import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * This is ReimbursementRequest Class.
 */
@Entity
@Table(name = "reimbursement")
public class ReimburseRequest {
  /**
   *This is a Constructor for ReimburseRequestOutDto.
   *
   *@param claimId Type of id.
   * @param str1 Type of expense.
   * @param date Date of expesnse.
   * @param str2 Path of the document.
   * @param amt Amount of expense.
   * @param str3 Comment on Expense.
   * @param str4 Status of the expense.
   * @param str5 Description on Expense.
   * @param users Object of an UserOutDto.
   */
  public ReimburseRequest(
      final int claimId,
      final String str1,
      final Date date,
      final String str2,
      final int amt,
      final String str3,
      final Status str4,
      final String str5,
      final User users) {
    super();
    this.id = claimId;
    this.expenseType = str1;
    this.dateOfExpense = new Date(date.getTime());
    this.documentFilePath = str2;
    this.amount = amt;
    this.comment = str3;
    this.claimStatus = str4;
    this.claimDesc = str5;
    this.user = users;
  }

  /**
 * Default Constructor.
 */
  public ReimburseRequest() {
    // TODO Auto-generated constructor stub
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reimbursement_id")
  private int id;

  @Column(name = "expense_type")
  private String expenseType;

  @Temporal(TemporalType.DATE)
  @Column(name = "date_of_expense")
  private Date dateOfExpense;

  @Column(name = "document_file_path")
  private String documentFilePath;

  private int amount;

  private String comment;

  @Enumerated(EnumType.STRING)
  private Status claimStatus;

  private String claimDesc;

  @ManyToOne
  private User user;

  /**
   * Getter of an id.
   *
 * @return Id to get.
 */
  public int getId() {
    return id;
  }

  /**
   * Setter of an Claim Id.
   *
 * @param claimId id to set.
 */
  public void setId(final int claimId) {
    this.id = claimId;
  }

  /**
   * This is a getter for expenseType.
   *
 * @return expenseType.
 */
  public String getExpenseType() {
    return expenseType;
  }

  /**
   * This is a setter for expenseType.
   *
   * @param type This is a expenseType field.
 */
  public void setExpenseType(final String type) {
    this.expenseType = type;
  }

  /**
   * This is a getter for Date of expense.
   *
 * @return It returns dateOfExpense.
 */
  public Date getDateOfExpense() {
    Date dt = new Date(dateOfExpense.getTime());
    return dt;
  }

  /**
   * This is a setter for Date of expense.
   *
 * @param date Takes dateOfExpense.
 */
  public void setDateOfExpense(final Date date) {
    Date dtDate = new Date(date.getTime());
    this.dateOfExpense = dtDate;
  }

  /**
   * Getter for Document file path.
   *
 * @return documentFilePath.
 */
  public String getDocumentFilePath() {
    return documentFilePath;
  }

  /**
   * Setter for documentFilePath.
   *
 * @param val takes documentFilePath.
 */
  public void setDocumentFilePath(final String val) {
    this.documentFilePath = val;
  }

  /**
   * Getter for amount.
   *
 * @return amount.
 */
  public int getAmount() {
    return amount;
  }

  /**
   * Setter for amount.
   *
 * @param val takes amount of an claim.
 */
  public void setAmount(final int val) {
    this.amount = val;
  }

  /**
   *Getter for comment.
   *
 * @return comment.
 */
  public String getComment() {
    return comment;
  }

  /**
   *Setter for comment.
   *
   * @param val Comment to set.
    */
  public void setComment(final String val) {
    this.comment = val;
  }

  /**
   *Status of the Claim.
   *
   * @return claimStatus.
    */
  public Status getClaimStatus() {
    return claimStatus;
  }

  /**
   *Setter for the status.
   *
   * @param val Claim status to set.
   */
  public void setClaimStatus(final Status val) {
    this.claimStatus = val;
  }

  /**
   * Description of the claim.
   *
  * @return claimDesc
  */
  public String getClaimDesc() {
    return claimDesc;
  }


  /**
   *Setter for the claim description.
   *
  * @param val Description to set.
    */
  public void setClaimDesc(final String val) {
    this.claimDesc = val;
  }

  /**
   * Getter for User object..
   *
   * @return user
    */
  public User getUser() {
    return user;
  }

  /**
   *Setter for UserOutDto object.
   *
   * @param val UserOutDto to set.
   */
  public void setUser(final User val) {
    this.user = val;
  }

  /**
 *This is a Hashcode method of the class.
 */
  @Override
  public int hashCode() {
    return Objects.hash(amount, claimDesc, claimStatus,
    comment, dateOfExpense, documentFilePath,
    expenseType, id, user);
  }

  /**
 *This is a equals method.
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
    ReimburseRequest other = (ReimburseRequest) obj;
    return amount == other.amount && Objects.equals(claimDesc, other.claimDesc)
      && claimStatus == other.claimStatus
      && Objects.equals(comment, other.comment)
      && Objects.equals(dateOfExpense, other.dateOfExpense)
      && Objects.equals(documentFilePath, other.documentFilePath)
      && Objects.equals(expenseType, other.expenseType)
      && id == other.id && Objects.equals(user, other.user);
  }

  /**
 *This is a toString method.
 */
  @Override
  public String toString() {
    return "ReimburseRequest [id=" + id + ", expenseType="
      + expenseType + ", dateOfExpense=" + dateOfExpense
      + ", documentFilePath=" + documentFilePath + ", amount=" + amount + ", comment=" + comment
      + ", claimStatus=" + claimStatus + ", claimDesc=" + claimDesc + "]";
  }
}
