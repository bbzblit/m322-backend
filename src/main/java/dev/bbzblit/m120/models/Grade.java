package dev.bbzblit.m120.models;

import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Grade {

	private String id = ObjectId.get().toHexString();

	@NotNull(message = "Your grade needs to have a weight")
	private double weight;

	@NotNull(message = "Your grade needs to have a value")
	private double value;

}
