package dev.bbzblit.m120.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.bbzblit.m120.models.AppUser;
import dev.bbzblit.m120.repository.AppUserRepository;

@Service
public class AppUserService {

	@Autowired
	AppUserRepository appUserRepository;

	
	public AppUser getAppUser(String appUserId) {
		
		if (appUserId == null) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Appuser id doesnt have to be null");
		}
		
		Optional<AppUser> appUser = appUserRepository.findById(appUserId);
		
		if(appUser.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No appuser with id " + appUserId + " has been found");
		}
		
		return appUser.get();
		
		
		
	}
	
	public AppUser storeAppUser(AppUser appUser) {
		return this.appUserRepository.save(appUser);
	}

	public Void deleteAppUser(String appUserId) {
		
		Optional<AppUser> appUser = this.appUserRepository.findById(appUserId);
		
		if (appUser.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No appuser with id " + appUserId + " has been found");
		}
		
		this.appUserRepository.deleteById(appUserId);
		
		return null;
	}

	public AppUser updateAppUser(@Valid AppUser appUser) {
		
		if(appUser == null) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Appuser doesnt hae to be null");
		}
		
		return this.appUserRepository.save(appUser);
	}
	
}
