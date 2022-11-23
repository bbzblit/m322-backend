package dev.bbzblit.m120.service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.Random;

import javax.print.attribute.standard.DateTimeAtCompleted;
import javax.servlet.http.Cookie;

import org.apache.tomcat.jni.Local;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;

import dev.bbzblit.m120.models.AppUser;
import dev.bbzblit.m120.models.Session;
import dev.bbzblit.m120.repository.AppUserRepository;
import dev.bbzblit.m120.repository.SessionRepository;

@Service
public class SessionService {

	@Autowired
	SessionRepository sessionRepository;
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	Random random = new Random();
	
	private void deleteOldSession(AppUser appUser) {
		Query query = new Query();
		query.addCriteria(Criteria.where("logedInAppUser._id").is(appUser.getId()));
		System.out.println(this.mongoTemplate.find(query, Session.class));
		this.mongoTemplate.findAllAndRemove(query, Session.class);
	}
	
	
	public String newSession(AppUser appUser) {
		
		this.deleteOldSession(appUser);
		
		byte[] array = new byte[300]; // length is bounded by 7
	    new Random().nextBytes(array);
	    String generatedString = new String(array, Charset.forName("UTF-8"));
	    
	    String hash = Hashing.sha256().hashString(generatedString, StandardCharsets.UTF_8).toString();
	    
	    Session session = new Session();
	    
	    session.setExpires(Timestamp.valueOf(LocalDateTime.now().plus(Duration.ofHours(1))).getTime());
	    session.setLogedInAppUser(appUser);
	    session.setSessionId(hash);
	    
	    this.sessionRepository.save(session);
	    
	    return hash;
	}
	
}
