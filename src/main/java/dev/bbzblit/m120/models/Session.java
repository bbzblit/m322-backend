package dev.bbzblit.m120.models;

import java.security.Timestamp;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Session {
	
	@Id
	private String id;
	
	private String sessionId;

	private AppUser logedInAppUser;
	
	private Long expires;
	
}
