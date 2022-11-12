package dev.bbzblit.m120.models;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class AppUser {

	String firstName;
	
	String lastName;
	
	String userName;
	
	String password; //Sha-256
	
	String email;
	
}
