package dev.bbzblit.m120.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import dev.bbzblit.m120.models.PasswortResetFlow;

@Repository
public interface PasswortResetFlowRepository extends MongoRepository<PasswortResetFlow, String> {

}
