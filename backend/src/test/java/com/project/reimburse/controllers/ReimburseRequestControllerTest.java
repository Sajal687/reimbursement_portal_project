package com.project.reimburse.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.reimburse.dtos.in.ReimburseRequestInDto;
import com.project.reimburse.dtos.out.ReimburseRequestOutDto;
import com.project.reimburse.dtos.out.UserOutDto;
import com.project.reimburse.entities.Status;
import com.project.reimburse.services.ReimburseRequestService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.wiring.ClassNameBeanWiringInfoResolver;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

@WebMvcTest(controllers = ReimburseRequestController.class)
@ExtendWith(MockitoExtension.class)
public class ReimburseRequestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReimburseRequestService reimburseRequestService;
	
	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testGetReimburseRequestsByUserEmail() throws Exception {
		UserOutDto userOutDto = new UserOutDto();
		userOutDto.setDateOfJoining(new Date());
		Mockito.when(reimburseRequestService.getAllClaimRequestsByUserEmail(anyString()))
				.thenReturn(Collections.singletonList(new ReimburseRequestOutDto(1, "Travel", new Date(), "image.png",
						100, "My Claim Comment.", Status.Pending, "User's Comment.", userOutDto)));

		ResultActions result = mockMvc.perform(get("/api/claims/reimburse-requests/{email}", "test@nucleusteq.com"));
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
	}

	@Test
	public void testGetAllClaims() throws Exception {
		UserOutDto userOutDto = new UserOutDto();
		userOutDto.setDateOfJoining(new Date());
		Mockito.when(reimburseRequestService.getAllClaimRequest())
				.thenReturn(Collections.singletonList(new ReimburseRequestOutDto(1, "Travel", new Date(), "image.png",
						100, "My Claim Comment.", Status.Pending, "User's Comment.", userOutDto)));

		ResultActions result = mockMvc.perform(get("/api/claims/allClaims"));

		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
	}

	@Test
	public void testUpdateClaimStatus_ApprovedStatus() throws Exception {
		UserOutDto userOutDto = new UserOutDto();
		userOutDto.setDateOfJoining(new Date());
		Mockito.when(reimburseRequestService.updateClaimRequestStatus(1, Status.Approved, "Approved comment"))
				.thenReturn(new ReimburseRequestOutDto(1, "Travel", new Date(), "image.png", 100, "My Claim Comment.",
						Status.Approved, "User's Comment.", userOutDto));

		ResultActions result = mockMvc
				.perform(put("/api/claims/updateStatus/{claimId}", 1).contentType(MediaType.APPLICATION_JSON)
						.content("{\"status\":\"Approved\",\"comment\":\"Approved comment\"}"));
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
	}

	@Test
	public void testUpdateClaimStatus_PendingStatus() throws Exception {
		UserOutDto userOutDto = new UserOutDto();
		userOutDto.setDateOfJoining(new Date());
		Mockito.when(reimburseRequestService.updateClaimRequestStatus(1, Status.Pending, "Pending comment"))
				.thenReturn(new ReimburseRequestOutDto(1, "Travel", new Date(), "image.png", 100, "My Claim Comment.",
						Status.Pending, "User's Comment.", userOutDto));

		ResultActions result = mockMvc
				.perform(put("/api/claims/updateStatus/{claimId}", 1).contentType(MediaType.APPLICATION_JSON)
						.content("{\"status\":\"Pending\",\"comment\":\"Pending comment\"}"));
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
	}

	@Test
	public void testUpdateClaimStatus_RejectedStatus() throws Exception {
		UserOutDto userOutDto = new UserOutDto();
		userOutDto.setDateOfJoining(new Date());
		Mockito.when(reimburseRequestService.updateClaimRequestStatus(1, Status.Rejected, "Rejected comment"))
				.thenReturn(new ReimburseRequestOutDto(1, "Travel", new Date(), "image.png", 100, "My Claim Comment.",
						Status.Rejected, "User's Comment.", userOutDto));

		ResultActions result = mockMvc
				.perform(put("/api/claims/updateStatus/{claimId}", 1).contentType(MediaType.APPLICATION_JSON)
						.content("{\"status\":\"Rejected\",\"comment\":\"Rejected comment\"}"));
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
	}

	@Test
	public void testGetAllAssignClaimsByStatus_PendingStatus() throws Exception {
		List<ReimburseRequestOutDto> claimList = new ArrayList<>();
		UserOutDto userOutDto = new UserOutDto();
		userOutDto.setDateOfJoining(new Date());
		claimList.add(new ReimburseRequestOutDto(1, "Travel", new Date(), "image.png", 100, "My Claim Comment.",
				Status.Pending, "User's Comment.", userOutDto));
		claimList.add(new ReimburseRequestOutDto(2, "Travel", new Date(), "image2.png", 1000, "My Claim Comment2.",
				Status.Pending, "User's Comment2.", userOutDto));

		Mockito.when(
				reimburseRequestService.getEmployeeAllClaimsByUserIdAndStatus("test@nucleusteq.com", Status.Pending))
				.thenReturn(claimList);

		ResultActions result = mockMvc
				.perform(get("/api/claims/employees/email/test@nucleusteq.com/status/Pending")
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}

	@Test
	public void testGetAllAssignClaimsByStatus_ApprovedStatus() throws Exception {
		UserOutDto userOutDto = new UserOutDto();
		userOutDto.setDateOfJoining(new Date());
		List<ReimburseRequestOutDto> claimList = new ArrayList<>();
		claimList.add(new ReimburseRequestOutDto(1, "Travel", new Date(), "image.png", 100, "My Claim Comment.",
				Status.Approved, "User's Comment.", userOutDto));
		claimList.add(new ReimburseRequestOutDto(2, "Travel", new Date(), "image2.png", 1000, "My Claim Comment2.",
				Status.Approved, "User's Comment2.", userOutDto));

		when(reimburseRequestService.getEmployeeAllClaimsByUserIdAndStatus("test@nucleusteq.com", Status.Approved))
				.thenReturn(claimList);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/claims/employees/email/test@nucleusteq.com/status/Approved")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}

	@Test
	public void testGetAllAssignClaimsByStatus_RejectedStatus() throws Exception {
		UserOutDto userOutDto = new UserOutDto();
		userOutDto.setDateOfJoining(new Date());
		List<ReimburseRequestOutDto> claimList = new ArrayList<>();
		claimList.add(new ReimburseRequestOutDto(1, "Travel", new Date(), "image.png", 100, "My Claim Comment.",
				Status.Rejected, "User's Comment.", userOutDto));
		claimList.add(new ReimburseRequestOutDto(2, "Travel", new Date(), "image2.png", 1000, "My Claim Comment2.",
				Status.Rejected, "User's Comment2.", userOutDto));

		when(reimburseRequestService.getEmployeeAllClaimsByUserIdAndStatus("test@nucleusteq.com", Status.Rejected))
				.thenReturn(claimList);

		mockMvc.perform(MockMvcRequestBuilders
				.get("/api/claims/employees/email/test@nucleusteq.com/status/Rejected")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}

	@Test
	public void testGetAllAssignEmployeeClaims() throws Exception {
		UserOutDto userOutDto = new UserOutDto();
		userOutDto.setDateOfJoining(new Date());
		List<ReimburseRequestOutDto> claimList = new ArrayList<>();
		claimList.add(new ReimburseRequestOutDto(1, "Travel", new Date(), "image.png", 100, "My Claim Comment.",
				Status.Pending, "User's Comment.", userOutDto));
		claimList.add(new ReimburseRequestOutDto(2, "Travel", new Date(), "image2.png", 1000, "My Claim Comment2.",
				Status.Approved, "User's Comment2.", userOutDto));

		String endpointUrl = "/api/claims/assignEmployeeClaims/test@nucleusteq.com";
		when(reimburseRequestService.getAllClaimsByManagerId("test@nucleusteq.com")).thenReturn(claimList);

		ResultActions result = mockMvc
				.perform(MockMvcRequestBuilders.get(endpointUrl).contentType(MediaType.APPLICATION_JSON));
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}

	@Test
	public void testGetAllClaimsByStatusName_PendingStatus() throws Exception {
		UserOutDto userOutDto = new UserOutDto();
		userOutDto.setDateOfJoining(new Date());
		List<ReimburseRequestOutDto> claimList = new ArrayList<>();
		claimList.add(new ReimburseRequestOutDto(1, "Travel", new Date(), "image.png", 100, "My Claim Comment.",
				Status.Pending, "User's Comment.", userOutDto));
		claimList.add(new ReimburseRequestOutDto(2, "Travel", new Date(), "image2.png", 1000, "My Claim Comment2.",
				Status.Pending, "User's Comment2.", userOutDto));

		when(reimburseRequestService.getAllClaimRequestByStatus(Status.Pending)).thenReturn(claimList);

		ResultActions result = mockMvc
				.perform(get("/api/claims/reimburse/claimsStatus/Pending").contentType(MediaType.APPLICATION_JSON));
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}

	@Test
	public void testGetAllClaimsByStatusName_ApprovedStatus() throws Exception {
		UserOutDto userOutDto = new UserOutDto();
		userOutDto.setDateOfJoining(new Date());
		List<ReimburseRequestOutDto> claimList = new ArrayList<>();
		claimList.add(new ReimburseRequestOutDto(1, "Travel", new Date(), "image.png", 100, "My Claim Comment.",
				Status.Approved, "User's Comment.", userOutDto));
		claimList.add(new ReimburseRequestOutDto(2, "Travel", new Date(), "image2.png", 1000, "My Claim Comment2.",
				Status.Approved, "User's Comment2.", userOutDto));

		when(reimburseRequestService.getAllClaimRequestByStatus(Status.Approved)).thenReturn(claimList);

		ResultActions result = mockMvc
				.perform(get("/api/claims/reimburse/claimsStatus/Approved").contentType(MediaType.APPLICATION_JSON));
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}

	@Test
	public void testGetAllClaimsByStatusName_RejectedStatus() throws Exception {
		UserOutDto userOutDto = new UserOutDto();
		userOutDto.setDateOfJoining(new Date());
		List<ReimburseRequestOutDto> claimList = new ArrayList<>();
		claimList.add(new ReimburseRequestOutDto(1, "Travel", new Date(), "image.png", 100, "My Claim Comment.",
				Status.Rejected, "User's Comment.", userOutDto));
		claimList.add(new ReimburseRequestOutDto(2, "Travel", new Date(), "image2.png", 1000, "My Claim Comment2.",
				Status.Rejected, "User's Comment2.", userOutDto));

		when(reimburseRequestService.getAllClaimRequestByStatus(Status.Rejected)).thenReturn(claimList);

		ResultActions result = mockMvc
				.perform(get("/api/claims/reimburse/claimsStatus/Rejected").contentType(MediaType.APPLICATION_JSON));
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
	}
	
	@Test
	public void testCreateUserClaim() throws Exception {
		String alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuilder randomString = new StringBuilder();
		for(int i = 0;i<10; i++) {
			int randomNum = random.nextInt(alphabets.length());
			randomString.append(alphabets.charAt(randomNum));
		}
	    MockMultipartFile mockFile = new MockMultipartFile(
	        "documentFile",
	        "examples.png",
	        "image/png",
	        randomString.toString().getBytes()
	    );
	    
	    Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = dateFormat.format(date);

	    mockMvc.perform(
	        MockMvcRequestBuilders.multipart("/api/claims/create")
	            .file(mockFile)
	            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
	            .param("expenseType", "Food")
	            .param("amount", "3000")
	            .param("dateOfExpense", formattedDate)
	            .param("comment", "Claim Comment")
	            .param("userEmail", "test@nucleusteq.com"))
	        .andExpect(MockMvcResultMatchers.status().isBadRequest())
	        .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Error occured while creating claim request."));
	}


	@Test
	public void testGetAllAssignEmployeeClaimsByEmailAndStatus() throws Exception {
		UserOutDto userOutDto = new UserOutDto();
		userOutDto.setDateOfJoining(new Date());		when(reimburseRequestService.getClaimByManagerIdAndStatus("test@nucleusteq.com", Status.Pending))
				.thenReturn(Collections.singletonList(new ReimburseRequestOutDto(1, "Travel", new Date(), "image.png",
						100, "User's comment", Status.Pending, "Claim description", userOutDto)));

		ResultActions result = mockMvc
				.perform(get("/api/claims/assignEmployeeClaimsByStatus/email/test@nucleusteq.com/status/Pending"));
		result.andExpect(MockMvcResultMatchers.status().isOk());
		result.andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1));
	}
}
