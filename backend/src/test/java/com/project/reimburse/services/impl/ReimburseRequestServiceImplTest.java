package com.project.reimburse.services.impl;

import com.project.reimburse.dtos.in.ReimburseRequestInDto;
import com.project.reimburse.dtos.out.ReimburseRequestOutDto;
import com.project.reimburse.dtos.out.UserOutDto;
import com.project.reimburse.entities.Designation;
import com.project.reimburse.entities.ReimburseRequest;
import com.project.reimburse.entities.Status;
import com.project.reimburse.entities.User;
import com.project.reimburse.exceptions.ResourceNotFoundException;
import com.project.reimburse.exceptions.UserNotFoundException;
import com.project.reimburse.repositories.ReimburseRequestRepo;
import com.project.reimburse.repositories.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReimburseRequestServiceImplTest {

	@InjectMocks
	private ReimburseRequestServiceImpl reimburseRequestService;

	@Mock
	private ReimburseRequestRepo reimburseRequestRepo;

	@Mock
	private UserRepo userRepo;

	
	@Test
	public void testCreateClaimRequest() {
	    User user = new User(1, "Test Name", Designation.Admin, "test@nucleusteq.com", new Date(), 0, "password@123", "Secret Answer", null, null);
	    when(userRepo.findByEmail("test@nucleusteq.com")).thenReturn(user);

	    ReimburseRequest newUserClaim = new ReimburseRequest(1, "Travel", new Date(), "path/to/document.png", 100, "Travel Expense", Status.Pending, "Pending Review", user);
	    when(reimburseRequestRepo.save(any(ReimburseRequest.class))).thenReturn(newUserClaim);

	    ReimburseRequestInDto requestDto = new ReimburseRequestInDto("Travel", new Date(), new MockMultipartFile("document.png", new byte[1024]), 100, "Travel Expense", Status.Pending, "Pending Review", "test@nucleusteq.com");
	    String filePath = "path/to/document.png";

	    ReimburseRequestOutDto expectedDto = new ReimburseRequestOutDto(1, "Travel", new Date(), "path/to/document.png", 100, "Travel Expense", Status.Pending, "Pending Review", new UserOutDto("Sajal Nema", Designation.Admin, "test@nucleusteq.com", new Date(), 0, null));

	    ReimburseRequestOutDto result = reimburseRequestService.createClaimRequest(requestDto, filePath);

	    verify(userRepo, times(1)).findByEmail(requestDto.getUserEmail());
	    verify(reimburseRequestRepo, times(1)).save(any(ReimburseRequest.class));

	    assertNotNull(result);
	    assertEquals(expectedDto.getExpenseType(), result.getExpenseType());
	}


	@Test
	public void testGetAllClaimRequestsByUserEmail() {
		String email = "test@nucleusteq.com";
		User user = new User();
		ReimburseRequest request1 = new ReimburseRequest();
		ReimburseRequest request2 = new ReimburseRequest();
		List<ReimburseRequest> requestList = new ArrayList<>();

		when(userRepo.findByEmail(email)).thenReturn(user);
		when(reimburseRequestRepo.findByUser(user)).thenReturn(requestList);

		List<ReimburseRequestOutDto> result = reimburseRequestService.getAllClaimRequestsByUserEmail(email);

		verify(userRepo, times(1)).findByEmail(email);
		verify(reimburseRequestRepo, times(1)).findByUser(user);

		assertNotNull(result);
		assertTrue(result.isEmpty());
	}

	@Test
	public void testGetAllClaimRequestByStatus() {
		Status status = Status.Pending;
		ReimburseRequest request1 = new ReimburseRequest();
		ReimburseRequest request2 = new ReimburseRequest();
		List<ReimburseRequest> requestList = new ArrayList<>();

		when(reimburseRequestRepo.findByClaimStatus(status)).thenReturn(requestList);

		List<ReimburseRequestOutDto> result = reimburseRequestService.getAllClaimRequestByStatus(status);

		verify(reimburseRequestRepo, times(1)).findByClaimStatus(status);

		assertNotNull(result);
	}

	@Test
	public void testUpdateClaimRequestStatus() {
		int claimId = 1;
		Status modifyStatus = Status.Approved;
		String comment = "Test Comment";
		User user = new User();
		user.setName("Test Name");
		user.setDateOfJoining(new Date());
		ReimburseRequest request = new ReimburseRequest();
		request.setUser(user);
		request.setDateOfExpense(new Date());
		ReimburseRequestOutDto expectedDto = new ReimburseRequestOutDto();

		when(reimburseRequestRepo.findById(claimId)).thenReturn(request);
		ReimburseRequestOutDto result = reimburseRequestService.updateClaimRequestStatus(claimId, modifyStatus,
				comment);

		verify(reimburseRequestRepo, times(1)).findById(claimId);

		assertNotNull(result);
		assertEquals(expectedDto.getExpenseType(), result.getExpenseType());
		assertEquals(modifyStatus, request.getClaimStatus());
	}

	@Test
	public void testUpdateClaimRequestStatusWithInvalidClaimId() {
		int invalidClaimId = -1;

		when(reimburseRequestRepo.findById(invalidClaimId)).thenReturn(null);

		assertThrows(ResourceNotFoundException.class, () -> {
			reimburseRequestService.updateClaimRequestStatus(invalidClaimId, Status.Approved, "Approved");
		});

		verify(reimburseRequestRepo).findById(invalidClaimId);
	}

	@Test
	public void testGetAllClaims() {
		String email = "test@nucleusteq.com";
		User user = new User();
		user.setDateOfJoining(new Date());
		user.setName("Test");
		ReimburseRequest request1 = new ReimburseRequest();
		request1.setDateOfExpense(new Date());
		request1.setUser(user);
		ReimburseRequest request2 = new ReimburseRequest();
		request2.setDateOfExpense(new Date());
		request2.setUser(user);
		List<ReimburseRequest> requestList = Arrays.asList(request1, request2);

		when(reimburseRequestRepo.findAll()).thenReturn(requestList);

		List<ReimburseRequestOutDto> result = reimburseRequestService.getAllClaimRequest();

		verify(reimburseRequestRepo, times(1)).findAll();
		assertNotNull(result);
	}

	@Test
	public void testGetEmployeeAllClaimsByUserIdAndStatus() {
		User user = new User();
		user.setId(1);
		user.setEmail("test@nucleusteq.com");

		List<ReimburseRequest> reimburseRequests = new ArrayList<>();

		when(userRepo.findByEmail("test@nucleusteq.com")).thenReturn(user);
		when(reimburseRequestRepo.findByUserAndClaimStatus(user, Status.Pending)).thenReturn(reimburseRequests);
		List<ReimburseRequestOutDto> result = reimburseRequestService
				.getEmployeeAllClaimsByUserIdAndStatus("test@nucleusteq.com", Status.Pending);

		assertEquals(reimburseRequests.size(), result.size());
	}

	@Test
	    public void testGetEmployeeAllClaimsByUserIdAndStatusUserNotFound() {
	        when(userRepo.findByEmail("test@nucleusteq.com")).thenReturn(null);

	        assertThrows(UserNotFoundException.class, () -> {
	            reimburseRequestService.getEmployeeAllClaimsByUserIdAndStatus("test@nucleusteq.com", Status.Pending);
	        });
	    }

	@Test
	public void testGetAllClaimsByManagerId() {
		User user = new User();
		user.setId(1);
		user.setEmail("test@nucleusteq.com");
		List<ReimburseRequest> reimburseRequests = new ArrayList<>();

		when(userRepo.findByEmail("test@nucleusteq.com")).thenReturn(user);
		when(reimburseRequestRepo.getAllClaimsByManagerId(user.getId())).thenReturn(reimburseRequests);

		List<ReimburseRequestOutDto> result = reimburseRequestService.getAllClaimsByManagerId("test@nucleusteq.com");

		assertEquals(reimburseRequests.size(), result.size());
	}

	@Test
	public void testGetClaimByManagerIdAndStatus() {
		User user = new User();
		user.setId(1);
		user.setEmail("test@nucleusteq.com");

		Status status = Status.Pending;

		List<ReimburseRequest> reimburseRequests = new ArrayList<>();

		when(userRepo.findByEmail("test@nucleusteq.com")).thenReturn(user);
		when(reimburseRequestRepo.getClaimsByClaimStatus(user.getId(), status)).thenReturn(reimburseRequests);

		List<ReimburseRequestOutDto> result = reimburseRequestService
				.getClaimByManagerIdAndStatus("test@nucleusteq.com", status);

		assertEquals(reimburseRequests.size(), result.size());
	}
}
