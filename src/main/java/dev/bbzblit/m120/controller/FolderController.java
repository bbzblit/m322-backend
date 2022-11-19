package dev.bbzblit.m120.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.bbzblit.m120.models.Folder;
import dev.bbzblit.m120.service.FolderService;

@RestController
public class FolderController {

	@Autowired
	private FolderService folderService;
	
	@PostMapping("/api/folder")
	public ResponseEntity<Folder> createFolder(@RequestBody @Valid Folder folder){
		return ResponseEntity.ok(this.folderService.saveFolder(folder));
	}
	
	@GetMapping("/api/folder")
	public ResponseEntity<Folder> getFolderById(@RequestParam(name = "id", required = true) String id){
		return ResponseEntity.ok(this.folderService.getFolderById(id));
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
