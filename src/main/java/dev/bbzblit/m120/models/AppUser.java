package dev.bbzblit.m120.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.HashIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Document
public class AppUser {

	String id;

	String firstName;
	
	String lastName;
	
	String userName;
	
	@JsonIgnore
	Boolean emailVerified; 
	
	@JsonIgnore
	String password; //Sha-256

	String email;
	
}
