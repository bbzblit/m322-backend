package dev.bbzblit.m120.models;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Document
@Data
public class Subject {

	private String id = ObjectId.get().toHexString();

	@NotNull(message = "Subject need to have a name")
	private String name;

	private List<Grade> grades = new ArrayList<Grade>();

	@JsonIgnore
	public void setGrade(Grade grade) {
		if (this.grades == null) {
			this.grades = new ArrayList<>();
		}
		grades.add(grade);
	}
}
