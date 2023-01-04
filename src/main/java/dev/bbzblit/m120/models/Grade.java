package dev.bbzblit.m120.models;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Grade {

	private String id = ObjectId.get().toHexString();

	private double weight;

	private double value;

}
