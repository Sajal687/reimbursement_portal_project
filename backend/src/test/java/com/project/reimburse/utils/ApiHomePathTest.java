package com.project.reimburse.utils;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

public class ApiHomePathTest {
	
	 	@Test
	    public void constantApiPath() {
	 		ApiHomePath apiHomePath = new ApiHomePath();
	 		assertEquals("/api/", apiHomePath.DEPARTMENT_HOME_PATH);
	 		
	 		assertEquals("/api", apiHomePath.CATEGORY_HOME_PATH);
	 		
	 		assertEquals("/api/user", apiHomePath.USER_HOME_PATH);
	 		
	 		assertEquals("/api/claims", apiHomePath.REIMBURSE_REQUEST_HOME_PATH);
	}
}
