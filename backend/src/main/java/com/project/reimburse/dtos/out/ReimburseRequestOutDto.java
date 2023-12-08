package com.project.reimburse.dtos.out;

import com.project.reimburse.entities.Status;
import java.util.Date;
import java.util.Objects;

/**
 * This is a ReimbursementRequest Dto Class.
 */
public class ReimburseRequestOutDto {
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
  public ReimburseRequestOutDto(
      final int claimId,
      final String str1,
      final Date date,
      final String str2,
      final int amt,
      final String str3,
      final Status str4,
      final String str5,
      final UserOutDto users) {
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
 * Default constructor.
 */
  public ReimburseRequestOutDto() {
   // TODO Auto-generated constructor stub
  }

  private int id;

  private String expenseType;

  private Date dateOfExpense;

  private String documentFilePath;

  private int amount;

  private String comment;

  private Status claimStatus;

  private String claimDesc;

  private UserOutDto user;

  /**
   * Setter of an Claim Id.
   *
 * @param claimId id to set.
 */
  public void setId(final int claimId) {
    this.id = claimId;
  }

  /**
   * Getter of an id.
   *
 * @return Id to get.
 */
  public int getId() {
    return id;
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
    Date dtDate = new Date(dateOfExpense.getTime());
    return dtDate;
  }

  /**
   * This is a setter for Date of expense.
   *
 * @param date Takes dateOfExpense.
 */
  public void setDateOfExpense(final Date date) {
    Date dt = new Date(date.getTime());
    dateOfExpense = dt;
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
 * @param path takes documentFilePath.
 */
  public void setDocumentFilePath(final String path) {
    this.documentFilePath = path;
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
 * @param amt takes amount of an claim.
 */
  public void setAmount(final int amt) {
    this.amount = amt;
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
 * @param status Claim status to set.
 */
  public void setClaimStatus(final Status status) {
    this.claimStatus = status;
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
* @param desc Description to set.
  */
  public void setClaimDesc(final String desc) {
    this.claimDesc = desc;
  }


  /**
 * Getter for UserOutDto object..
 *
 * @return user
  */
  public UserOutDto getUser() {
    UserOutDto userCopy = new UserOutDto(user.getName(), user.getDesignation(),
        user.getEmail(), user.getDateOfJoining(), user.getManagerId(), user.getDepartmentOutDto());
    return userCopy;
  }

  /**
 *Setter for UserOutDto object.
 *
 * @param users UserOutDto to set.
 */
  public void setUser(final UserOutDto users) {
    this.user = users;
  }

  /**
 *This is a hashCode method.
 */
  @Override
  public int hashCode() {
    return Objects.hash(amount, claimDesc, claimStatus,
     comment, dateOfExpense, documentFilePath, expenseType, id, user);
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
    ReimburseRequestOutDto other = (ReimburseRequestOutDto) obj;
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
    return "ReimburseRequestOutDto [id=" + id + ", expenseType="
      + expenseType + ", dateOfExpense=" + dateOfExpense
      + ", claimStatus=" + claimStatus + ", claimDesc=" + claimDesc + ", user=" + user + "]";
  }
}
