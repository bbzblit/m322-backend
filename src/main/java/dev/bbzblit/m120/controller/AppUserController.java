package dev.bbzblit.m120.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import dev.bbzblit.m120.models.AppUser;
import dev.bbzblit.m120.repository.AppUserRepository;
import dev.bbzblit.m120.service.AppUserService;

@RestController
public class AppUserController {

	@Autowired
	AppUserService appUserService;
	
	@PostMapping("/api/appuser/register") 
	public ResponseEntity<AppUser> register(@RequestBody @Valid AppUser appUser){
		return ResponseEntity.ok(this.appUserService.saveAppUser(appUser));
	}
	
	@GetMapping("/api/appuser")
	public ResponseEntity<AppUser> getAppUserById(@RequestParam(name = "id", required = true) String appUserId){
		return ResponseEntity.ok(this.appUserService.getAppUser(appUserId));
	}
	
	@DeleteMapping("/api/appuser")
	public ResponseEntity<Void> deleteAppUserById(@RequestParam(name = "id", required = true) String appUserId){
		return ResponseEntity.ok(this.appUserService.deleteAppUser(appUserId));
	}
	
	@PutMapping("/api/appuser")
	public ResponseEntity<AppUser> updateAppUser(@RequestBody @Valid AppUser appUser){		
		
		return ResponseEntity.ok(this.appUserService.updateAppUser(appUser));
	}
}
