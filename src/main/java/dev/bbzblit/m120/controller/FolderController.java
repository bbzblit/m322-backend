package dev.bbzblit.m120.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

import dev.bbzblit.m120.models.AppUser;
import dev.bbzblit.m120.models.Folder;
import dev.bbzblit.m120.service.FolderService;
import dev.bbzblit.m120.service.SessionService;

@RestController
public class FolderController {

	@Autowired
	private FolderService folderService;
	
	@Autowired
	private SessionService sessionService;
	
	@GetMapping("/api/folder")
	public ResponseEntity<List<Folder>> getAllFolder(@CookieValue("SESSIONID") String sessionId){
		AppUser appUser = this.sessionService.isLogedIn(sessionId);
		
		List<Folder> folders = this.folderService.getFolderByAppUser(appUser);
		
		return ResponseEntity.ok(folders);
	}
	
	
	@PostMapping("/api/folder")
	public ResponseEntity<Folder> createFolder(@RequestBody @Valid Folder folder, @CookieValue("SESSIONID") String sessionId){
		AppUser appUser = this.sessionService.isLogedIn(sessionId);
		folder.setOwner(appUser);
		return ResponseEntity.ok(this.folderService.saveFolder(folder));
	}
	
	@DeleteMapping("/api/folder")
	public ResponseEntity<Void> deleteFolderById(@RequestParam(name = "id", required = true) String id){
		this.folderService.deleteFolderById(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/api/folder")
	public ResponseEntity<Folder> updateFolder(@RequestBody @Valid Folder folder){
		return ResponseEntity.ok(this.folderService.updateFolder(folder));
	}
	
	
}
