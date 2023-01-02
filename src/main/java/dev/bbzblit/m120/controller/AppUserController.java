package dev.bbzblit.m120.controller;

import javax.servlet.http.Cookie;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.bbzblit.m120.models.AppUser;
import dev.bbzblit.m120.models.LoginModel;
import dev.bbzblit.m120.repository.AppUserRepository;
import dev.bbzblit.m120.service.AppUserService;
import dev.bbzblit.m120.service.SessionService;

@RestController
public class AppUserController {

	@Autowired
	AppUserService appUserService;

	@Autowired
	SessionService sessionService;

	@PostMapping("/api/appuser/register")
	public ResponseEntity<AppUser> register(@RequestBody @Valid AppUser appUser) {
		appUser.setEmailVerified(true); //TODO: Remove if later implemented
		appUser = this.appUserService.saveAppUser(appUser);
		return ResponseEntity.status(HttpStatus.OK).body(appUser);
	}

	@PostMapping("/api/appuser/login")
	public ResponseEntity<AppUser> login(@RequestBody @Valid LoginModel loginModel){
		AppUser appUser = this.appUserService.findByEmailOrUsernameAndPassword(loginModel);
		String sessionId = this.sessionService.newSession(appUser);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", "SESSIONID=" + sessionId + "; Max-Age=604800; Path=/; Secure; HttpOnly");
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(appUser);	
	}
	
	@GetMapping("/api/appuser/relogin")
	public ResponseEntity<AppUser> islogedin(@CookieValue( name ="SESSIONID", required = false) String sessionid){
		if(sessionid == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to login");
		}
		return ResponseEntity.ok(this.sessionService.isLogedIn(sessionid));
	}
	
	@GetMapping("/api/appuser/logout")
	public ResponseEntity<Void> logout(@CookieValue("SESSIONID") String sessionid){
		this.sessionService.logout(sessionid);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", "SESSIONID=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;");
		return ResponseEntity.ok().headers(headers).body(null);
	}
	
	
	
	@GetMapping("/api/appuser")
	public ResponseEntity<AppUser> getAppUserById(@RequestParam(name = "id", required = true) String appUserId) {
		return ResponseEntity.ok(this.appUserService.getAppUser(appUserId));
	}

	@DeleteMapping("/api/appuser")
	public ResponseEntity<Void> deleteAppUserById(@RequestParam(name = "id", required = true) String appUserId) {
		return ResponseEntity.ok(this.appUserService.deleteAppUser(appUserId));
	}

	@PutMapping("/api/appuser")
	public ResponseEntity<AppUser> updateAppUser(@RequestBody @Valid AppUser appUser) {

		return ResponseEntity.ok(this.appUserService.updateAppUser(appUser));
	}
	
	@GetMapping("/api/appuser/getid")
	public ResponseEntity<AppUser> getUserIdByEmailOrUsername(@RequestParam(name = "identifier", required = true) String usernameOrEmail){
		
		AppUser appUser = this.appUserService.findByEmailOrUsername(usernameOrEmail);
		appUser.setFirstName(null);
		appUser.setLastName(null);
		return ResponseEntity.ok(appUser);
	}
}
