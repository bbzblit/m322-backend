package dev.bbzblit.m120.models;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.PasswordAuthentication;

import javax.validation.constraints.Email;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Document
public class AppUser {

	private String id;

	private String firstName;
	
	private String lastName;
	
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
	private String email;
	
}
