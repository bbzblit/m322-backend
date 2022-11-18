package dev.bbzblit.m120.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import dev.bbzblit.m120.models.AppUser;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, String> {

}
