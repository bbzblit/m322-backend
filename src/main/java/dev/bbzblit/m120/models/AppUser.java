package dev.bbzblit.m120.models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.PasswordAuthentication;

import javax.annotation.RegEx;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import dev.bbzblit.m120.models.validation.UniqueUsername;
import lombok.Data;

@Data
@Document
public class AppUser {

	private String id;

	@NotNull(message = "You have to provide a FirstName")
	private String firstName;
	

	@NotNull(message = "You have to provide a LastName")
	private String lastName;
	
	@UniqueUsername
	private String userName;
	
	@JsonIgnore
	private Boolean emailVerified; 
	
	@JsonIgnore
	private String password; //Sha-256
	
	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@JsonProperty
	public void setPassword(final String password) {

		this.password = password;
	}
	
	@Email
	private String email;
	
}
