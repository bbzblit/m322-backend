package dev.bbzblit.m120.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.bbzblit.m120.models.Grade;

@Repository
public interface GradeRepository extends MongoRepository<Grade, String>{

}
