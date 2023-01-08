package dev.bbzblit.m120.service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.google.common.hash.Hashing;

import dev.bbzblit.m120.models.AppUser;
import dev.bbzblit.m120.models.LoginModel;
import dev.bbzblit.m120.models.PasswortResetFlow;
import dev.bbzblit.m120.repository.AppUserRepository;
import dev.bbzblit.m120.repository.PasswortResetFlowRepository;

@Service
public class AppUserService {

	@Autowired
	AppUserRepository appUserRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	@Value("${server.url}")
	String url;

	@Autowired
	private JavaMailSender emailSender;

	@Autowired
	PasswortResetFlowRepository passwortResetFlowRepository;

	@Value("${security.login.salt}")
	String salt;

	public AppUser getAppUser(String appUserId) {

		if (appUserId == null) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Appuser id doesnt have to be null");
		}

		Optional<AppUser> appUser = appUserRepository.findById(appUserId);

		if (appUser.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"No appuser with id " + appUserId + " has been found");
		}

		return appUser.get();

	}

	public AppUser saveAppUser(AppUser appUser) {
		String hasedPasswd = Hashing.sha256().hashString(appUser.getPassword() + salt, StandardCharsets.UTF_8)
				.toString();

		appUser.setPassword(hasedPasswd);

		return this.appUserRepository.save(appUser);
	}

	public Void deleteAppUser(String appUserId) {

		Optional<AppUser> appUser = this.appUserRepository.findById(appUserId);

		if (appUser.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"No appuser with id " + appUserId + " has been found");
		}

		this.appUserRepository.deleteById(appUserId);

		return null;
	}


	public AppUser findByEmailOrUsernameAndPassword(LoginModel loginModel) {

		loginModel.setPassword(
				Hashing.sha256().hashString(loginModel.getPassword() + salt, StandardCharsets.UTF_8).toString());

		Optional<AppUser> optionalAppUser = this.appUserRepository.getByUsernameAndPassword(loginModel.getPassword(),
				loginModel.getUserNameOrEmail());

		if (optionalAppUser.isEmpty()) {
			optionalAppUser = this.appUserRepository.getByEmailAndPassword(loginModel.getPassword(),
					loginModel.getUserNameOrEmail());
		}

		if (optionalAppUser.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong Username/Email or Password");
		}

		AppUser appUser = optionalAppUser.get();

		if (!appUser.getEmailVerified()) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
					"You first have to verifiy your email befor login");
		}

		return appUser;
	}

	public AppUser findByEmailOrUsername(String usernameOrEmail) {

		Optional<AppUser> optionalAppUser = this.appUserRepository.getByUsername(usernameOrEmail);

		if (optionalAppUser.isEmpty()) {
			optionalAppUser = this.appUserRepository.getByEmail(usernameOrEmail);
		}

		if (optionalAppUser.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					"No AppUser found with the email or username " + usernameOrEmail);
		}

		AppUser appUser = optionalAppUser.get();

		return appUser;
	}

	public void sendResetEmail(PasswortResetFlow passwortResetFlow) {
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("no-reply@fusiva.ch");
        message.setTo(passwortResetFlow.getAppUser().getEmail()); 
        message.setSubject("Password Reset"); 
        message.setText(
        		"Your link to reset your password:" +
        		this.url + "/reset/" + passwortResetFlow.getOtp() 
        		);
        try {
        	emailSender.send(message);
        } catch (Exception e) {
        	throw new ResponseStatusException( HttpStatus.SERVICE_UNAVAILABLE,"Can't send mail pls try it later again");
        }
        
	}

	public PasswortResetFlow initPasswortReset(AppUser appUser) {

		PasswortResetFlow passwortResetFlow = new PasswortResetFlow();

		Query query = new Query();
		query.addCriteria(Criteria.where("appUser._id").is(appUser.getId()));
		this.mongoTemplate.findAndRemove(query, PasswortResetFlow.class);
		passwortResetFlow.setAppUser(appUser);

		byte[] array = new byte[505]; // length is bounded by 7
		new Random().nextBytes(array);
		String generatedString = new String(array, Charset.forName("UTF-8"));

		String hash = Hashing.sha256().hashString(generatedString, StandardCharsets.UTF_8).toString();

		passwortResetFlow.setOtp(hash);

		this.sendResetEmail(passwortResetFlow);

		this.passwortResetFlowRepository.save(passwortResetFlow);

		return passwortResetFlow;
	}
	
	public PasswortResetFlow getPasswortResetFlowByOtp(String otp) {
		
		PasswortResetFlow flow = this.passwortResetFlowRepository.findByOtp(otp);
		
		if( flow == null ) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Your password reset link is not valid");
		}
		
		this.passwortResetFlowRepository.deleteById(flow.getId());
		
		return flow;
		
	}
	
	public AppUser updatePassword(AppUser appUser, String password) {
		appUser.setPassword(
				Hashing.sha256().hashString(password + salt, StandardCharsets.UTF_8).toString());

		return this.appUserRepository.save(appUser);
	}
}
