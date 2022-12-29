package dev.bbzblit.m120.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Subject {

	@Id
	private String id;
	
	private String name;
	
	@DBRef
	private List<Grade> grades;
	
	
}
