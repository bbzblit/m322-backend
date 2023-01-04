package dev.bbzblit.m120.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class PasswortResetFlow {
	
	@Id
	private String id;
	
	private String otp;
	
	@DBRef
	private AppUser appUser;
}
