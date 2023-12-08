package com.project.reimburse.services.impl;

import com.project.reimburse.dtos.in.ReimburseRequestInDto;
import com.project.reimburse.dtos.out.ReimburseRequestOutDto;
import com.project.reimburse.dtos.out.UserOutDto;
import com.project.reimburse.entities.ReimburseRequest;
import com.project.reimburse.entities.Status;
import com.project.reimburse.entities.User;
import com.project.reimburse.exceptions.ResourceNotFoundException;
import com.project.reimburse.exceptions.UserNotFoundException;
import com.project.reimburse.repositories.ReimburseRequestRepo;
import com.project.reimburse.repositories.UserRepo;
import com.project.reimburse.services.ReimburseRequestService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This is a Implementation Class of ReimburseRequest Service.
 */
@Service
public class ReimburseRequestServiceImpl implements ReimburseRequestService {
  /**
   * ReimburseRequestRepo Instance.
   */
  @Autowired
  private ReimburseRequestRepo reimburseRequestRepo;

  /**
  * UserRepo Instance.
  */
  @Autowired
  private UserRepo userRepo;

  /**
   * This is a Logger Object.
   */
  private final Logger log = LoggerFactory
         .getLogger(ReimburseRequestServiceImpl.class);

  /**
 * Implementation method for createClaimRequest function.
 *
 * @param reimburseRequestDto Object of ReimburseRequestInDto.
 * @return ReimburseRequestOutDto object.
 */
  @Override
  public ReimburseRequestOutDto createClaimRequest(
      final ReimburseRequestInDto reimburseRequestDto, final String filePath) {
    log.info("Creating cliam by user email {}", reimburseRequestDto.getUserEmail());
    User user = userRepo.findByEmail(reimburseRequestDto.getUserEmail());
    ReimburseRequest newUserClaim = inDtoToReimburseRequest(reimburseRequestDto);
    newUserClaim.setClaimStatus(Status.Pending);
    newUserClaim.setDocumentFilePath(filePath);
    newUserClaim.setUser(user);
    ReimburseRequest savedClaim = reimburseRequestRepo.save(newUserClaim);
    ReimburseRequestOutDto respDto = reimburseRequestToOutDto(savedClaim);
    return respDto;
  }

  /**
  *This method fetch all claims of Managers and Employee.
  *
  *@param email User email to find all claim.
  *@return List of ReimburseRequestOutDto object.
  */
  @Override
  public List<ReimburseRequestOutDto> getAllClaimRequestsByUserEmail(final String email) {
    User user = userRepo.findByEmail(email);
    log.info("Getting all Claims by user email {}", email);
    List<ReimburseRequest> reimburseRequests = reimburseRequestRepo.findByUser(user);
    List<ReimburseRequestOutDto> reimburseRequestDtos = reimburseRequests.stream()
              .map((request) -> reimburseRequestToOutDto(request))
              .collect(Collectors.toList());
    return reimburseRequestDtos;
  }

  /**
  * This is a method to get all claims.
  *
  *@return List of all available claims.
  */
  @Override
  public List<ReimburseRequestOutDto> getAllClaimRequest() {
    List<ReimburseRequest> allClaimsRequests = this.reimburseRequestRepo.findAll();
    log.info("Getting all claim request");
    List<ReimburseRequestOutDto> respDtos =
        allClaimsRequests.stream().map(
          req -> reimburseRequestToOutDto(req)).collect(Collectors.toList());
    return respDtos;
  }

  /**
 *This is a method used to get all claims by userId and claim status.
 *
 *@param email Email of an manager.
 *@param status status of an claim.
 *@return a list of claims object.
 */
  @Override
  public List<ReimburseRequestOutDto> getEmployeeAllClaimsByUserIdAndStatus(final String email,
      final Status status)  {
    User user = userRepo.findByEmail(email);
    log.info("Getting all user claims by user email {} and status {}", email, status);
    if (Objects.isNull(user)) {
      throw new UserNotFoundException(String.format(
         "No user exists with this email and password %s", email));
    }
    List<ReimburseRequest> allClaims = reimburseRequestRepo.findByUserAndClaimStatus(user, status);
    List<ReimburseRequestOutDto> resp = allClaims.stream()
        .map((claim) -> reimburseRequestToOutDto(claim)).collect(Collectors.toList());
    return resp;
  }

  /**
  *This is a method to get all claim request by status.
  *
  *@param status Status to set.
  *@return List of ReimburseRequestOutDto object.
  */
  @Override
  public List<ReimburseRequestOutDto> getAllClaimRequestByStatus(final Status status) {
    List<ReimburseRequest> allClaimsList =
        this.reimburseRequestRepo.findByClaimStatus(status);
    log.info("Getting all claim request by claim status {}.", status);
    List<ReimburseRequestOutDto> allClaimsDtos =
        allClaimsList.stream().map((claim) -> reimburseRequestToOutDto(claim))
        .collect(Collectors.toList());
    return allClaimsDtos;
  }


  /**
 *This is updateClaimRequestStatus method implementation.
 *
 *@param claimId Actually ID to find for.
 *@param modifyStatus status to set.
 *@param comment Comment to set.
 */
  @Override
  public ReimburseRequestOutDto updateClaimRequestStatus(final int claimId,
      final Status modifyStatus, final String comment) {
    ReimburseRequest request = this.reimburseRequestRepo.findById(claimId);
    if (Objects.isNull(request)) {
      throw new ResourceNotFoundException("Claim Request ", "ID ", claimId);
    }
    log.info("Updating Claim with claim ID {} to status {} and comment {} ",
        claimId, modifyStatus, comment);
    request.setClaimStatus(modifyStatus);
    if (modifyStatus.equals(Status.Rejected)) {
      request.setClaimDesc(comment);
    }
    this.reimburseRequestRepo.save(request);
    ReimburseRequestOutDto respDto = this.reimburseRequestToOutDto(request);
    return respDto;
  }

  /**
   *This is a method to get all claim request by status.
   *
   *@param email User email.
   *@return List of ReimburseRequestOutDto object.
   */
  @Override
  public List<ReimburseRequestOutDto> getAllClaimsByManagerId(final String email) {
    User user = userRepo.findByEmail(email);
    List<ReimburseRequest> assignEmpClaims =
        this.reimburseRequestRepo.getAllClaimsByManagerId(user.getId());
    log.info("Update claim by Manager by email {}", email);
    List<ReimburseRequestOutDto> resp =
         assignEmpClaims.stream().map(
           (claim) -> reimburseRequestToOutDto(claim)).collect(Collectors.toList());
    return resp;
  }

  /**
   *This is a method to get all claim request by status.
   *
   * @param email User email.
   * @param claimStatus Claim Status.
   * @return List of ReimburseRequestOutDto object.
   */
  @Override
  public List<ReimburseRequestOutDto> getClaimByManagerIdAndStatus(final String email,
      final Status claimStatus) {
    User user = userRepo.findByEmail(email);
    List<ReimburseRequest> assEmpClaimsByStatus =
        this.reimburseRequestRepo.getClaimsByClaimStatus(user.getId(), claimStatus);
    log.info("Getting all claims by email {} and Status {}", email, claimStatus);
    List<ReimburseRequestOutDto> resp =
        assEmpClaimsByStatus.stream().map(
          (claim) -> reimburseRequestToOutDto(claim)).collect(Collectors.toList());
    return resp;
  }

  /**
   * This is Custom mapper for mapping ReimburseRequestInDto Object to ReimburseRequest.
   *
 * @param reimburseRequestInDto ReimburseRequestInDto Object .
 * @return ReimburseRequest Object.
 */
  public ReimburseRequest inDtoToReimburseRequest(
      final ReimburseRequestInDto reimburseRequestInDto) {
    ReimburseRequest reimburseRequest = new ReimburseRequest();
    reimburseRequest.setExpenseType(reimburseRequestInDto.getExpenseType());
    reimburseRequest.setDateOfExpense(reimburseRequestInDto.getDateOfExpense());
    reimburseRequest.setAmount(reimburseRequestInDto.getAmount());
    reimburseRequest.setComment(reimburseRequestInDto.getComment());
    reimburseRequest.setClaimStatus(Status.Pending);
    reimburseRequest.setClaimDesc(reimburseRequestInDto.getClaimDesc());
    return reimburseRequest;
  }

  /**
   *This is Custom mapper to map object ReimburseRequest to ReimburseRequestOutDto Object.
   *
   * @param reimburseRequest Object to map.
   * @return ReimburseRequestOutDto Object.
 */
  public ReimburseRequestOutDto reimburseRequestToOutDto(final ReimburseRequest reimburseRequest) {
    User user = reimburseRequest.getUser();
    UserOutDto userOutDto = new UserOutDto();
    userOutDto.setName(user.getName());
    userOutDto.setDesignation(user.getDesignation());
    userOutDto.setEmail(user.getEmail());
    userOutDto.setDateOfJoining(user.getDateOfJoining());
    userOutDto.setManagerId(user.getManagerId());

    ReimburseRequestOutDto reimburseRequestOutDto = new ReimburseRequestOutDto();
    reimburseRequestOutDto.setId(reimburseRequest.getId());
    reimburseRequestOutDto.setExpenseType(reimburseRequest.getExpenseType());
    reimburseRequestOutDto.setDateOfExpense(reimburseRequest.getDateOfExpense());
    reimburseRequestOutDto.setDocumentFilePath(reimburseRequest.getDocumentFilePath());
    reimburseRequestOutDto.setAmount(reimburseRequest.getAmount());
    reimburseRequestOutDto.setComment(reimburseRequest.getComment());
    reimburseRequestOutDto.setClaimStatus(reimburseRequest.getClaimStatus());
    reimburseRequestOutDto.setClaimDesc(reimburseRequest.getClaimDesc());
    reimburseRequestOutDto.setUser(userOutDto);
    return reimburseRequestOutDto;
  }
}
