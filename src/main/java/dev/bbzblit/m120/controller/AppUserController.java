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
import dev.bbzblit.m120.models.PasswordReset;
import dev.bbzblit.m120.models.PasswortResetFlow;
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
	public ResponseEntity<AppUser> register( @Valid @RequestBody  AppUser appUser) {
		System.out.println("BBB");
		appUser.setEmailVerified(true); // TODO: Remove if later implemented
		appUser = this.appUserService.saveAppUser(appUser);
		return ResponseEntity.status(HttpStatus.OK).body(appUser);
	}

	@PostMapping("/api/appuser/login")
	public ResponseEntity<AppUser> login(@RequestBody @Valid LoginModel loginModel) {
		AppUser appUser = this.appUserService.findByEmailOrUsernameAndPassword(loginModel);
		String sessionId = this.sessionService.newSession(appUser);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", "SESSIONID=" + sessionId + "; Max-Age=604800; Path=/; Secure; HttpOnly");
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(appUser);
	}

	@GetMapping("/api/appuser/relogin")
	public ResponseEntity<AppUser> islogedin(@CookieValue(name = "SESSIONID", required = false) String sessionid) {
		if (sessionid == null) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You need to login");
		}
		return ResponseEntity.ok(this.sessionService.isLogedIn(sessionid));
	}

	@GetMapping("/api/appuser/logout")
	public ResponseEntity<Void> logout(@CookieValue("SESSIONID") String sessionid) {
		this.sessionService.logout(sessionid);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Set-Cookie", "SESSIONID=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;");
		return ResponseEntity.ok().headers(headers).body(null);
	}

	@GetMapping("/api/appuser")
	public ResponseEntity<AppUser> getAppUserById(@RequestParam(name = "id", required = true) String appUserId) {
		AppUser appUser = this.appUserService.getAppUser(appUserId);
		appUser.setFirstName(null);
		appUser.setLastName(null);
		return ResponseEntity.ok(appUser);
	}

	@DeleteMapping("/api/appuser")
	public ResponseEntity<Void> deleteAppUserById(@RequestParam(name = "id", required = true) String appUserId) {
		return ResponseEntity.ok(this.appUserService.deleteAppUser(appUserId));
	}

	@GetMapping("/api/appuser/getid")
	public ResponseEntity<AppUser> getUserIdByEmailOrUsername(
			@RequestParam(name = "identifier", required = true) String usernameOrEmail) {

		AppUser appUser = this.appUserService.findByEmailOrUsername(usernameOrEmail);
		appUser.setFirstName(null);
		appUser.setLastName(null);
		return ResponseEntity.ok(appUser);
	}

	@PostMapping("/api/appuser/passwortreset/initflow")
	public ResponseEntity<Void> initPasswordResetFlow(@RequestParam(name = "email") String email) {

		if (!email.matches("^(.+)@(\\S+)$")) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
					"The provided email address is not in a valid email address format your string: " + email);
		}

		AppUser appUser = this.appUserService.findByEmailOrUsername(email);

		this.appUserService.initPasswortReset(appUser);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/api/appuser/passwortreset")
	public ResponseEntity<AppUser> resetPassword(@RequestBody PasswordReset passwordReset) {
		
		PasswortResetFlow flow =  this.appUserService.getPasswortResetFlowByOtp(passwordReset.getOtp());
		
		AppUser appUser = flow.getAppUser();
		
		appUser = this.appUserService.updatePassword(appUser, passwordReset.getPassword());
		
		return ResponseEntity.ok(appUser);

	}

}
