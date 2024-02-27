package com.example.demo.config;

import java.io.IOException;
import java.io.Serializable;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtExceptionHandler extends Exception implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -98569558953243875L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws IOException, ServletException{
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		
	
	}
//		System.out.println("---------------JwtAuthenticationEntryPoint----------------------");
//
//		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//		OutputStream responseStream = response.getOutputStream();
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.writeValue(responseStream, "Unable to read JSON value");
//		responseStream.flush();

		//// response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
		// authException.getStackTrace());
	

}
