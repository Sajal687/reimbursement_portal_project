package com.project.reimburse.dtos.in;

import com.project.reimburse.entities.Status;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

/**
 * This is ReimburseRequestInDto Class.
 */
public class ReimburseRequestInDto {
  /**
   *This is a Constructor for ReimburseRequestOutDto.
   *
   * @param str1 Type of expense.
   * @param date Date of expense.
   * @param file Path of the document.
   * @param amt Amount of expense.
   * @param str3 Comment on Expense.
   * @param str4 Status of the expense.
   * @param str5 Description on Expense.
   * @param users Object of an UserOutDto.
   */
  public ReimburseRequestInDto(
      final @NotEmpty(message = "Expense Type is Required.") String str1,
      final @NotNull(message = "Date of Expense is Required.") Date date,
      final @NotEmpty(message = "Document Path is Required.")  MultipartFile file,
      final @Min(value = 1,
      message = "Expense Amount must be greater than or equal to 1.") int amt,
      final String str3,
      final Status str4,
      final String str5,
      final @NotNull(message = "User Email is Required.") String users) {
    super();
    this.expenseType = str1;
    this.dateOfExpense = new Date(date.getTime());
    this.documentFile = file;
    this.amount = amt;
    this.comment = str3;
    this.claimStatus = str4;
    this.claimDesc = str5;
    this.userEmail = users;
  }

  /**
  * No argument constructor of ReimburseRequestInDto.
  */
  public ReimburseRequestInDto() {
    // TODO Auto-generated constructor stub
  }

  @NotBlank(message = "Expense Type is Required.")
  private String expenseType;

  @NotNull(message = "Date of Expense is Required.")
  @Temporal(TemporalType.DATE)
  @Past(message = "Date of expense is only past and present.")
  private Date dateOfExpense;

  @NotNull(message = "Claim Document file is required.")
  private MultipartFile documentFile;

  @Positive(message = "Amount must be a positive integer.")
  private int amount;

  @NotBlank(message = "Claim comment is required.")
  @Size(max = 250, message = "Comment length is not be more than 250 characters.")
  private String comment;

  private Status claimStatus;

  private String claimDesc;


  @NotBlank(message = "Email is required.")
  @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@nucleusteq.com$",
      message = "Please enter a valid email, it must contains nucleusteq domain.")
  private String userEmail;

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
  * @param val This is a expenseType field.
  */
  public void setExpenseType(final String val) {
    this.expenseType = val;
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
  * @param val Takes dateOfExpense.
  */
  public void setDateOfExpense(final Date val) {
    Date dt = new Date(val.getTime());
    this.dateOfExpense = dt;
  }

  /**
  * Getter for Document file path.
  *
  * @return documentFilePath.
  */
  public MultipartFile getDocumentFile() {
    return documentFile;
  }

  /**
  * Setter for documentFilePath.
  *
  * @param val takes documentFilePath.
  */
  public void setDocumentFile(final MultipartFile val) {
    this.documentFile = val;
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
  * Getter for user mail.
  *
  * @return userEmail
  */
  public String getUserEmail() {
    return userEmail;
  }

  /**
  *Setter for User mail.
  *
  * @param val email to set.
  */
  public void setUserEmail(final String val) {
    this.userEmail = val;
  }

  /**
  *This is a hashCode method.
  */
  @Override
  public int hashCode() {
    return Objects.hash(amount, claimDesc, claimStatus, comment,
    dateOfExpense, documentFile, expenseType, userEmail);
  }

  /**
  *This is a equals method of class.
  *
  *@param val takes object as an argument.
  */
  @Override
  public boolean equals(final Object val) {
    if (this == val) {
      return true;
    }
    if (val == null) {
      return false;
    }
    if (getClass() != val.getClass()) {
      return false;
    }
    ReimburseRequestInDto other = (ReimburseRequestInDto) val;
    return amount == other.amount && Objects.equals(
         claimDesc, other.claimDesc) && claimStatus == other.claimStatus
         && Objects.equals(comment, other.comment)
         && Objects.equals(dateOfExpense, other.dateOfExpense)
         && Objects.equals(documentFile, other.documentFile)
         && Objects.equals(expenseType, other.expenseType)
         && Objects.equals(userEmail, other.userEmail);
  }



  /**
  *This is a toString method of a class.
  */
  @Override
  public String toString() {
    return "ReimburseRequestInDto [expenseType="
           + expenseType + ", dateOfExpense=" + dateOfExpense + ", documentFile="
           + documentFile + ", amount=" + amount + ", comment="
           + comment + ", claimStatus=" + claimStatus
           + ", claimDesc=" + claimDesc + ", userEmail=" + userEmail + "]";
  }
}
