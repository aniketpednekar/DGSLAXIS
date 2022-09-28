package com.dgsl.imp.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	String uplodeFile(MultipartFile filename, String label);
}
