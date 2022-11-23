package dev.bbzblit.m120.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.bbzblit.m120.models.Grade;
import dev.bbzblit.m120.repository.GradeRepository;

@Service
public class GradeService {
	
	@Autowired
	GradeRepository gradeRepository;
	
	public Grade saveGrade(Grade grade) {
		
		if(grade == null) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Grade has to be not null");
		}
		
		return this.gradeRepository.save(grade);
	}
	
	public Grade getGradeById(String id) {
		
		Optional<Grade> grade = this.gradeRepository.findById(id);
		
		if(grade.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There doesnt exist a grade with the id " + id);
		}
		
		return grade.get();
		
	}
	
	
	public void deleteGradeById(String id) {
		
		Optional<Grade> grade = this.gradeRepository.findById(id);
		
		if(grade.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There doesnt exist a grade with the id " + id);
		}
		
		this.gradeRepository.deleteById(id);
	}
	
	public Grade updateGrade(Grade grade) {
		if(grade == null) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Grade doesnt have to be null");
		}
		
		return this.gradeRepository.save(grade);
	}
	
	
}
