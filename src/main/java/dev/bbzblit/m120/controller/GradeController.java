package dev.bbzblit.m120.controller;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import dev.bbzblit.m120.models.Folder;
import dev.bbzblit.m120.models.Grade;
import dev.bbzblit.m120.models.Subject;
import dev.bbzblit.m120.repository.FolderRepository;
import dev.bbzblit.m120.service.FolderService;
import dev.bbzblit.m120.service.GradeService;

@RestController
public class GradeController {

	@Autowired
	FolderService folderService;

	@PostMapping("/api/grade")
	public ResponseEntity<Subject> addGrade(@RequestBody @Valid Grade grade,
			@RequestParam(name = "folderId", required = true) String folderId,
			@RequestParam(name = "subjectId", required = true) String subjectId) {

		Folder folder = folderService.getFolderById(folderId);
		List<Subject> subjects = folder.getSubjects();

		Subject foundSubject = null;

		for (int i = 0; i < subjects.size(); i++) {
			if (subjects.get(i).getId().equals(subjectId)) {
				folder.getSubjects().get(i).setGrade(grade);
				foundSubject = folder.getSubjects().get(i);
			}
		}

		if (foundSubject == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					" error occurred while processing your request. Please try again or contact administrator (error 1001)");
		}

		this.folderService.saveFolder(folder);

		return ResponseEntity.ok(foundSubject);
	}

	@DeleteMapping("/api/grade")
	public ResponseEntity<Subject> removeGrade(@RequestParam(name = "folderId") String folderId,
			@RequestParam(name = "subjectId") String subjectId, @RequestParam(name = "gradeId") String gradeId) {
		Folder folder = this.folderService.getFolderById(folderId);
		List<Subject> subjects = folder.getSubjects();

		Subject foundSubject = null;

		for (int i = 0; i < subjects.size(); i++) {
			if (subjects.get(i).getId().equals(subjectId)) {
				List<Grade> grades = subjects.get(i).getGrades();
				for (int j = 0; j < grades.size(); j++) {
					if (grades.get(j).getId().equals(gradeId)) {
						grades.remove(j);
						foundSubject = subjects.get(i);
					}
				}
			}
		}

		if (foundSubject == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
					" error occurred while processing your request. Please try again or contact administrator (error 1001)");
		}

		this.folderService.saveFolder(folder);
		
		return ResponseEntity.ok(foundSubject);
	}

}
