package com.project.reimburse.services;

import com.project.reimburse.dtos.in.ReimburseRequestInDto;
import com.project.reimburse.dtos.out.ReimburseRequestOutDto;
import com.project.reimburse.entities.Status;
import java.util.List;

/**
 * This is a ReimburseRequestService Interface.
 */
public interface ReimburseRequestService {

  /**
  * This is a Service method for Creating Claim Request.
  *
  * @param reimburseRequestDto Object to Create Claim Request.
  * @param filePath String contains path of the file.
  * @return ReimburseRequestOutDto Object.
  */
  ReimburseRequestOutDto createClaimRequest(ReimburseRequestInDto reimburseRequestDto,
      String filePath);

  /**
   * This is a method to get all claim request by user email.
   *
  * @param email Email to find for.
  * @return List of ReimburseRequestOutDto Object.
  */
  List<ReimburseRequestOutDto> getAllClaimRequestsByUserEmail(String email);

  /**
   * It is a getting all cliams by Status.
   *
   * @param status Status to find all claims.
   * @return List of all ReimburseRequestOutDto Object.
 */
  List<ReimburseRequestOutDto> getAllClaimRequestByStatus(Status status);

  /**
   * Update Claim request status , and admin description comment by claimId.
   *
  * @param claimId Claim Id to find for.
  * @param modifyStatus Modify Status to set.
  * @param comment Comment to set for.
  * @return Modify ReimburseRequestOutDto Object.
 */
  ReimburseRequestOutDto updateClaimRequestStatus(int claimId, Status modifyStatus, String comment);

  /**
  * This method is used to get all claims of all Employees and Managers.
  *
  * @return It returns list of all claims.
  */
  List<ReimburseRequestOutDto> getAllClaimRequest();

  /**
   * This method is used to get all claims of all Employees and Managers.
   *
   * @param email User email.
   * @param status Claim status.
   * @return It returns list of all claims.
   */
  List<ReimburseRequestOutDto> getEmployeeAllClaimsByUserIdAndStatus(String email, Status status);

  /**
   * This method is used to get all claims of all Employees and Managers.
   *
   * @param email User email.
   * @return It returns list of all claims.
   */
  List<ReimburseRequestOutDto> getAllClaimsByManagerId(String email);

  /**
   * This method is used to get all claims of all Employees and Managers.
   *
   * @param email User email.
   * @param claimStatus Claim status.
   * @return It returns list of all claims.
   */
  List<ReimburseRequestOutDto> getClaimByManagerIdAndStatus(String email, Status claimStatus);
}
