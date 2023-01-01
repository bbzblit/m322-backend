package dev.bbzblit.m120.controller;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
public class GradeController {

	@Autowired
	FolderService folderService;

	@PostMapping("/api/grade")
	public ResponseEntity<Grade> addGrade(@RequestBody @Valid Grade grade,
			@RequestParam(name = "folderId", required = true) String folderId,
			@RequestParam(name = "subjectId", required = true) String subjectId) {

		Folder folder = folderService.getFolderById(folderId);
		List<Subject> subjects = folder.getSubjects();

		boolean found = false;

		for (int i = 0; i < subjects.size(); i++) {
			if (subjects.get(i).getId().equals(subjectId)) {
				folder.getSubjects().get(i).setGrade(grade);
				found = true;
			}
		}

		if (!found) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, " error occurred while processing your request. Please try again or contact administrator (error 1001)" );	
		}
		
		this.folderService.saveFolder(folder);

		return ResponseEntity.ok(grade);
	}
}
