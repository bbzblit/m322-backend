package dev.bbzblit.m120.models;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Folder {

	List<Subject> subjects;
	
	AppUser owner;
	
	List<AppUser> viewAccess;
	
	List<AppUser> writeAccess;
	
}
