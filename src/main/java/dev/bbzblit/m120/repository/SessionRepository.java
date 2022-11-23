package dev.bbzblit.m120.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import dev.bbzblit.m120.models.Session;

@Repository
public interface SessionRepository extends MongoRepository<Session, String> {
}
