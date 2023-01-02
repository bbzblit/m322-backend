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
	public Optional<AppUser> getByEmailAndPassword(String password, String email);
	

	@Query("{ password : ?0, userName : ?1 }")
	public Optional<AppUser> getByUsernameAndPassword(String password, String userName);
	
	@Query("{ email : ?0 }")
	public Optional<AppUser> getByEmail(String email);
	

	@Query("{ userName : ?0 }")
	public Optional<AppUser> getByUsername(String userName);
	
}
