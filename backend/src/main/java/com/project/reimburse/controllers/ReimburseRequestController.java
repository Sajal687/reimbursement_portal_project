package com.project.reimburse.controllers;

import com.project.reimburse.dtos.in.ReimburseRequestInDto;
import com.project.reimburse.dtos.out.ReimburseRequestOutDto;
import com.project.reimburse.entities.Status;
import com.project.reimburse.exceptions.BadRequestException;
import com.project.reimburse.exceptions.ResourceAlreadyExist;
import com.project.reimburse.services.ReimburseRequestService;
import com.project.reimburse.utils.ApiHomePath;
import com.project.reimburse.utils.MessageConstants;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * This is a Reimbursement Request Controller.
 */
@CrossOrigin
@RestController
@RequestMapping(ApiHomePath.REIMBURSE_REQUEST_HOME_PATH)
public class ReimburseRequestController {
  /**
  * ReimburseRequestService Instance.
  */
  @Autowired
  private ReimburseRequestService reimbursementService;

  /**
  * This is a Logger Object.
  */
  private final Logger log = LoggerFactory
         .getLogger(ReimburseRequestController.class);

  /**
  *This is a Controller method for creating a claim request.
  *
  * @param claimDto Request of object.
  * @return Message corresponding to the status.
  */
  @PostMapping("/create")
  public ResponseEntity<Map<String, String>> createUserClaim(
      final @Valid  @ModelAttribute ReimburseRequestInDto claimDto
  ) {
    log.info("Creating a new claim request for user with email: {}", claimDto.getUserEmail());
    String documentFilePath = null;

    try {
      byte[] inputFileByteArr = claimDto.getDocumentFile().getBytes();
      String relativePathToImages = "src/main/resources/Images/";
      String currentWorkingDirectory = System.getProperty("user.dir");
      String absoluteImagePath = currentWorkingDirectory + File.separator + relativePathToImages;
      File folderPath = new File(absoluteImagePath);
      File[] storedFiles = folderPath.listFiles();
      if (storedFiles != null) {
        for (File file : storedFiles) {
          String extension = file.getName().substring(file.getName().lastIndexOf('.') + 1);
          if (extension.equals("png") || extension.equals("jpeg")  || extension.equals("jpg")) {
            //File currFile = new File(MessageConstants.UPLOAD_DIRECTORY + "/" + file.getName());
            byte[] array2 = Files.readAllBytes(file.toPath());
            boolean res = Arrays.equals(array2, inputFileByteArr);
            if (res) {
              throw new ResourceAlreadyExist(MessageConstants.FILE_ALREADY_MESSAGE);
            }
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    if (!claimDto.getDocumentFile().isEmpty()) {
      String fileName = System.currentTimeMillis() + "_"
          + claimDto.getDocumentFile().getOriginalFilename();
      String uploadDirectory = MessageConstants.UPLOAD_DIRECTORY + fileName;
      File dest = new File(uploadDirectory);
      try {
        claimDto.getDocumentFile().transferTo(dest);
        documentFilePath = fileName;
        log.info("Claim request document uploaded successfully: {}", documentFilePath);
      } catch (IOException e) {
        log.error("Failed to upload claim request"
            + " document for user with email: {}", claimDto.getUserEmail(), e);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
    } else {
      throw new BadRequestException(MessageConstants.CLAIM_DOCUMENT_REQUIRED);
    }

    ReimburseRequestOutDto createdClaimReq =
        reimbursementService.createClaimRequest(claimDto, documentFilePath);
    log.info("Claim request created successfully for user with email: {}", claimDto.getUserEmail());
    Map<String, String> messageMap = new HashMap<String, String>();
    if (Objects.isNull(createdClaimReq)) {
      messageMap.put(MessageConstants.API_MESSAGE, MessageConstants.CLAIM_CREATED_UNSUCCESSFUL);
      return new ResponseEntity<Map<String, String>>(messageMap, HttpStatus.BAD_REQUEST);
    }
    messageMap.put(MessageConstants.API_MESSAGE, MessageConstants.CLAIM_CREATED_SUCCESS);
    return new ResponseEntity<Map<String, String>>(messageMap, HttpStatus.CREATED);
  }

  /**
  * This Controller is used to get all reimbursement requests of User.
  *
  * @param email This is a email of an User.
  * @return List of all ReimburseRequest outDto.
  */
  @GetMapping("/reimburse-requests/{email}")
   public ResponseEntity<List<ReimburseRequestOutDto>> getReimburseRequestsByUserEmail(
       final @PathVariable String email) {
    log.info("Fetching reimbursement requests for user with email: {}", email);
    List<ReimburseRequestOutDto> reimburseRequestOutDto =
         reimbursementService.getAllClaimRequestsByUserEmail(email);
    log.info("Fetched {} reimbursement requests for user with email: {}",
         reimburseRequestOutDto.size(), email);
    return ResponseEntity.ok(reimburseRequestOutDto);
  }

  /**
  * This is a Controller for Getting all claims by status name.
  *
  * @param claimStatus Actual string to set.
  * @return The List of ReimburseRequestOutDto Object.
  */
  @GetMapping("/reimburse/claimsStatus/{claimStatus}")
  public ResponseEntity<List<ReimburseRequestOutDto>> getAllClaimsByStatusName(
      final @PathVariable String claimStatus) {
    log.info("Fetching claims with status: {}", claimStatus);
    Status requestStatus = null;
    if (claimStatus.equals("Pending")) {
      requestStatus = Status.Pending;
    } else if (claimStatus.equals("Approved")) {
      requestStatus = Status.Approved;
    } else {
      requestStatus = Status.Rejected;
    }
    List<ReimburseRequestOutDto> resp =
        this.reimbursementService.getAllClaimRequestByStatus(requestStatus);
    log.info("Fetched {} claims with status: {}", resp.size(), claimStatus);

    return new ResponseEntity<List<ReimburseRequestOutDto>>(resp, HttpStatus.OK);
  }

  /**
  *This controller method is used to get all claims of employees by manager email and status.
  *
  * @param email Email of an Manager.
  * @param claimStatus Status of an claim.
  * @return List of objects of ReimburseRequestOutDto.
  */
  @GetMapping("/assignEmployeeClaimsByStatus/email/{email}/status/{claimStatus}")
  public ResponseEntity<List<ReimburseRequestOutDto>> getAllAssignEmployeeClaimsByEmailAndStatus(
      final @PathVariable String email, final @PathVariable String claimStatus) {
    log.info("Fetching claims with status: {} assigned to manager with email: {}",
        claimStatus, email);
    Status requestStatus = null;
    if (claimStatus.equals("Pending")) {
      requestStatus = Status.Pending;
    } else if (claimStatus.equals("Approved")) {
      requestStatus = Status.Approved;
    } else {
      requestStatus = Status.Rejected;
    }
    List<ReimburseRequestOutDto> respDtos =
        this.reimbursementService.getClaimByManagerIdAndStatus(email, requestStatus);
    log.info("Fetched {} claims with status: {} assigned to manager with email: {}",
        respDtos.size(), claimStatus, email);
    return new ResponseEntity<List<ReimburseRequestOutDto>>(respDtos, HttpStatus.OK);
  }

  /**
  *This is a controller method for getting all employees of particular employees.
  *
  * @param email Takes email of an manager.
  * @return a list of all employees.
  */
  @GetMapping("/assignEmployeeClaims/{email}")
  public ResponseEntity<List<ReimburseRequestOutDto>> getAllAssignEmployeeClaims(
      final @PathVariable String email) {
    log.info("Fetching employees assigned to manager with email: {}", email);
    List<ReimburseRequestOutDto> respDtos =
        this.reimbursementService.getAllClaimsByManagerId(email);
    log.info("Fetched {} employees assigned to manager with email: {}", respDtos.size(), email);
    return new ResponseEntity<List<ReimburseRequestOutDto>>(respDtos, HttpStatus.OK);
  }

  /**
  *This controller method is used to find all employees claims by email and claim status.
  *
  * @param email Email of an employee to search for.
  * @param claimStatus Status of an claim.
  * @return a list of all employee.
  */
  @GetMapping("/employees/email/{email}/status/{claimStatus}")
  public ResponseEntity<List<ReimburseRequestOutDto>> getAllAssignClaimsByStatus(
      final @PathVariable String email, final @PathVariable String claimStatus) {
    log.info("Fetching claims with status: {} for employee with email: {}", claimStatus, email);
    Status requestStatus = null;
    if (claimStatus.equals("Pending")) {
      requestStatus = Status.Pending;
    } else if (claimStatus.equals("Approved")) {
      requestStatus = Status.Approved;
    } else {
      requestStatus = Status.Rejected;
    }
    List<ReimburseRequestOutDto> respDtos =
        reimbursementService.getEmployeeAllClaimsByUserIdAndStatus(email, requestStatus);
    log.info("Fetched {} claims with status: {} for employee with email: {}",
        respDtos.size(), claimStatus, email);
    return new ResponseEntity<List<ReimburseRequestOutDto>>(respDtos, HttpStatus.OK);
  }

  /**
  *This is a Controller method to fetch all claims of employees.
  *
  * @return A list of all employees.
  */
  @GetMapping("/allClaims")
  public ResponseEntity<List<ReimburseRequestOutDto>> getAllClaims() {
    log.info("Fetching all claims of employees");
    List<ReimburseRequestOutDto> respDto = this.reimbursementService.getAllClaimRequest();
    log.info("Fetched {} claims of employees", respDto.size());

    return new ResponseEntity<List<ReimburseRequestOutDto>>(respDto, HttpStatus.OK);
  }

  /**
  * This is a Controller for Updating claim Status.
  *
  * @param claimId Claim Id of an Claim.
  * @param claimStatus Map of the key-value pair consist of status or comment.
  * @return Object of ReimburseRequestOutDto.
  */
  @PutMapping("/updateStatus/{claimId}")
  public ResponseEntity<ReimburseRequestOutDto> updateClaimStatus(
      final @PathVariable int claimId, final @RequestBody Map<String, String> claimStatus) {
    log.info("Updating status for claim with ID: {}", claimId);
    Status requestStatus = null;
    if (claimStatus.get("status").equals("Pending")) {
      requestStatus = Status.Pending;
    } else if (claimStatus.get("status").equals("Approved")) {
      requestStatus = Status.Approved;
    } else {
      requestStatus = Status.Rejected;
    }

    String adminComment = "";
    if (!claimStatus.get("comment").equals("")) {
      adminComment = claimStatus.get("comment");
    }

    ReimburseRequestOutDto resp = this.reimbursementService.updateClaimRequestStatus(
        claimId, requestStatus, adminComment);

    log.info("Status updated for claim with ID: {}", claimId);
    return new ResponseEntity<ReimburseRequestOutDto>(resp, HttpStatus.OK);
  }

  /**
  *Image of an claim.
  *
  * @param img Image to get.
  * @return Image in a byte format.
  * @throws IOException If image is not not than throws an IOException.
  */
  @GetMapping(value = "/image/{img}",
      produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<Map<String, byte[]>> getImage(
      final @PathVariable("img") String img) throws IOException {
    log.info("Fetching image: {}", img);
    ClassPathResource imgFile = new ClassPathResource("Images/" + img);
    byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
    Map<String, byte[]> map = new HashMap<String, byte[]>();
    map.put("image", bytes);
    log.info("Fetched image: {}", img);
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(map);
  }
}
