package dev.bbzblit.m120.models;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Folder {
	
	@Id
	private String id;
	
	@NotNull(message = "You have to provide a title")
	private String title;
	
	private List<Subject> subjects;
	
	@DBRef
	private AppUser owner; //Ref to AppUser
	
	private List<String> viewAccess; //Ref to appuser id with view access
	
	private List<String> writeAccess; //Ref to appuser id with write access
	
}
