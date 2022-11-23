package dev.bbzblit.m120.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import dev.bbzblit.m120.models.Grade;

public interface GradeRepository extends MongoRepository<Grade, String>{

}
