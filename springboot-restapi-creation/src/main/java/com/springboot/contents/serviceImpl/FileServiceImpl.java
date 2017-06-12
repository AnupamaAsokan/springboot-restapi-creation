package com.springboot.contents.serviceImpl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.contents.domain.FileMetadata;
import com.springboot.contents.SearchCriteria;
import com.springboot.contents.InmemoryFile;
import com.springboot.contents.service.FileService;
import com.springboot.contents.util.FileUtil;

/**
 * Implementation for File service operations
 * @author anupama
 *
 */
@Service
public class FileServiceImpl implements FileService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Resource
	InmemoryFile inmemFileRepo;

	public FileMetadata saveFile(MultipartFile uploadedFile) throws Exception {
		
		FileMetadata fileMetadata = FileUtil.buildFileMetadata(uploadedFile);
		LOGGER.debug("Saving " +fileMetadata.getFileName());

		FileMetadata fileMetadataExists = inmemFileRepo.findByName(fileMetadata.getFileName());
		if (fileMetadataExists != null) {
			fileMetadata.setId(fileMetadataExists.getId());
		}
		String filePath = FileUtil.writeFileToLocalDirectory(uploadedFile, fileMetadata);
		fileMetadata.setFilePath(filePath);
		fileMetadata = inmemFileRepo.save(fileMetadata);
		return fileMetadata;
	}
	
	public FileMetadata getFileMetaData(String fileName) throws Exception {
		LOGGER.debug("Getting FileMetaData for " + fileName);
		return inmemFileRepo.findByName(fileName);
	}

	public org.springframework.core.io.Resource getFileToBeDownloaded(FileMetadata fileMetadata) throws Exception {
		LOGGER.debug("Downloading file " + fileMetadata.getFileName());
		
		Path path = Paths.get(fileMetadata.getFilePath());
	    ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
	    return resource;
	}

	public List<UUID> getFileIDs(SearchCriteria searchCriteria) throws Exception {
		LOGGER.debug("getFileIDs based on crireria ");
		
		Iterable<FileMetadata> fileIterable = null;
		List<UUID> fileIDs = new ArrayList<UUID>();
		if (searchCriteria == null  || FileUtil.isEmpty(searchCriteria)) {
			fileIterable = inmemFileRepo.findAll();
		} else if (searchCriteria != null && searchCriteria.getFileType() != null && searchCriteria.getSizeInBytes()!= null) {
			fileIterable = inmemFileRepo.findByTypeAndSize(searchCriteria.getFileType(), searchCriteria.getSizeInBytes());
		} else if (searchCriteria != null && searchCriteria.getFileType() != null) {
			fileIterable = inmemFileRepo.findByType(searchCriteria.getFileType());
		}else if (searchCriteria != null && searchCriteria.getSizeInBytes()!= null) {
			fileIterable = inmemFileRepo.findBySize(searchCriteria.getSizeInBytes());
		} else if (searchCriteria != null && searchCriteria.getFileName() != null) {
			FileMetadata fileMetaData = inmemFileRepo.findByName(searchCriteria.getFileName());
			if (fileMetaData != null)
				fileIDs.add(fileMetaData.getId());
		} 
		if(fileIterable != null)
			for (FileMetadata fileMetadata : fileIterable)
				fileIDs.add(fileMetadata.getId());
			
		return fileIDs;
	}
	
	public Iterable<FileMetadata> getFiles() throws Exception {
		LOGGER.debug("Getting all FileMetadata" );
		
		return inmemFileRepo.findAll();
	}
	
	/**
	 * @return the repository
	 */
	public InmemoryFile getRepository() {
		return inmemFileRepo;
	}
	/**
	 * @param repository the repository to set
	 */
	public void setRepository(InmemoryFile repository) {
		this.inmemFileRepo = repository;
	}
}

