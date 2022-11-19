package dev.bbzblit.m120.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.bbzblit.m120.models.Folder;
import dev.bbzblit.m120.repository.FolderRepository;

@Service
public class FolderService {

	@Autowired
	FolderRepository folderRepository;
	
	public Folder saveFolder(Folder folder) {
		
		if(folder == null) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Folder has to be not null");
		}
		
		return this.folderRepository.save(folder);
	}
	
	public Folder getFolderById(String id) {
		
		Optional<Folder> folder = this.folderRepository.findById(id);
		
		if(folder.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There doesnt exist a folder with the id" + id);
		}
		
		return folder.get();
		
		
		
	}

	public void deleteFolderById(String id) {

		Optional<Folder> folder = this.folderRepository.findById(id);
		
		if(folder.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There doesnt exist a folder with the id" + id);
		}
		
		this.folderRepository.deleteById(id);
		
	}
	
	public Folder updateFolder(Folder folder) {
		if(folder == null) {
			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Folder doesnt have to be null");
		}
		
		return this.folderRepository.save(folder);
	}
	
}
