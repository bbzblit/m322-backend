package dev.bbzblit.m120.models;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LoginModel {
	
	@NotNull(message = "You can't loging using no password")
	private String password;
	
	@NotNull(message = "You can't loging using no email/username")
	private String userNameOrEmail;
	
}
