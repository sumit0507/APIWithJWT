package com.demoLogin.LoginAPIWithJWT.customResponse;

import com.demoLogin.LoginAPIWithJWT.customResponse.ApiResponse;

public class ApiResponse {
	
	

	
		private String message;

		public ApiResponse(String message) {
			
			this.message = message;
		}

		public String getMessage() {
			return message;
		}

		public static ApiResponse ok(String string) {
			// TODO Auto-generated method stub
			return null;
		}

		
		
		




}
