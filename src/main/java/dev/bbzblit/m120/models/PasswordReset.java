package dev.bbzblit.m120.models;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class PasswordReset {
	
	@NotNull(message = "Pleas provider a otp token")
	private String otp;
	
	@NotNull(message = "Pleas provider a new password")
	private String password;
	
}
