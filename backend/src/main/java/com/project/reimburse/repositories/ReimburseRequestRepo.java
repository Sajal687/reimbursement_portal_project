package com.project.reimburse.repositories;


import com.project.reimburse.entities.ReimburseRequest;
import com.project.reimburse.entities.Status;
import com.project.reimburse.entities.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * This is a ReimburseRequestRepo class.
 */
public interface ReimburseRequestRepo extends JpaRepository<ReimburseRequest, Integer> {
  /**
   *This is a Custom JPA method to find all Reimbursement Request of the User.
 *
 * @param user User object.
 * @return List of all the ReimbursementRequest.
 */
  List<ReimburseRequest> findByUser(User user);

  /**
   *This is a custom jpa method for finding all claims by user.
   *
 * @param user User to find for.
 * @param claimStatus Status to find for.
 *
 * @return List of object of claims.
 */
  List<ReimburseRequest> findByUserAndClaimStatus(User user, Status claimStatus);

  /**
   * This is a Custom JPA method for finding all Claims based on Status.
   *
   * @param claimStatus Status of the Claim.
   * @return List of all ReimburseRequest.
   */
  List<ReimburseRequest> findByClaimStatus(Status claimStatus);

  /**
  * This Custom method is used to find Claim Request by Id.
  *
  * @param id Actual ID to find for.
  * @return ReimburseRequest.
  */
  ReimburseRequest findById(int id);

  /**
   *This is a Custom JPQL Query for getting all claims for corresponding managerID.
   *
 * @param managerId ID of manager to search for.
 * @return List of Object of ReimburseRequest.
 */
  @Query("SELECT r FROM ReimburseRequest r WHERE r.user.managerId=:managerId")
  List<ReimburseRequest> getAllClaimsByManagerId(@Param("managerId") int managerId);

  /**
   *This is a custom JPQL Query for getting all claims by managerId and Status.
   *
 * @param managerId Id for manager to find.
 * @param claimStatus status to find for.
 * @return list of claims.
 */
  @Query("SELECT r FROM ReimburseRequest r WHERE r.user.managerId=:managerId AND"
      + " r.claimStatus=:claimStatus")
  List<ReimburseRequest> getClaimsByClaimStatus(@Param("managerId") int managerId,
      @Param("claimStatus") Status claimStatus);
}
