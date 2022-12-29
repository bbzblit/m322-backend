package dev.bbzblit.m120.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Folder {
	
	@Id
	private String id;
	
	private String title;
	
	@DBRef
	private List<Subject> subjects;
	
	@DBRef
	private AppUser owner; //Ref to AppUser
	
	@DBRef
	private List<AppUser> viewAccess; //Ref to AppUser
	
	@DBRef
	private List<AppUser> writeAccess; //Ref to AppUser
	
}
