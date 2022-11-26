package dev.bbzblit.m120.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import dev.bbzblit.m120.models.AppUser;

@Repository
public interface AppUserRepository extends MongoRepository<AppUser, String> {

	@Query("{ password : ?0, email : ?1 }")
	public Optional<AppUser> getByEmail(String password, String email);
	

	@Query("{ password : ?0, userName : ?1 }")
	public Optional<AppUser> getByUsername(String password, String userName);
	
}
