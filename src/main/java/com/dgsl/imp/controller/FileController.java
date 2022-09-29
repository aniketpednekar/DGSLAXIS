package com.dgsl.imp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dgsl.imp.service.FileService;
import com.dgsl.imp.utility.FileUtility;
import com.dgsl.imp.utility.ReadPropertyFile;
import com.lowagie.text.Document;

@RestController
public class FileController {

	@Autowired
	FileService fileService;

	@CrossOrigin
	@PostMapping(value = "/uplodeFile")
	public @ResponseBody String uplodeFile(@RequestBody MultipartFile filename, @RequestParam("transactionId") String transactionId) {
		String lStatus = "Failed";
		System.out.println("File Name :::: " + filename.getOriginalFilename() + " transactionId :::: " + transactionId);
		FileUtility lFileUtility = new FileUtility();
		try {
			String lLocation = lFileUtility.saveFileToFolder(filename);
			lStatus = fileService.uplodeFile(lLocation, transactionId);
			lFileUtility.deleteFolder();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lStatus;
	}
	
	@GetMapping(value = "/pdf")
	public ResponseEntity<InputStreamResource> getTermsConditions() throws FileNotFoundException {
		Document d = new Document();
		String filePath = "E:\\MyDocument\\";
        String lFileName = "test.txt";
		File file = new File(filePath + lFileName);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "inline;filename=" +lFileName);

		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
		String mimeType = "application/docs";
		String extension = lFileName.substring(lFileName.lastIndexOf(".") + 1);

		if (extension != null && extension != "") {
			if (ReadPropertyFile.getInstance().getMIMEProp().getProperty(extension.toUpperCase()) != null) {
				mimeType = ReadPropertyFile.getInstance().getMIMEProp().getProperty(extension.toUpperCase());
			}
		}

		return ResponseEntity.ok().headers(headers).contentLength(file.length())
                .contentType(MediaType.parseMediaType(mimeType))
				.body(resource);
	}


}
