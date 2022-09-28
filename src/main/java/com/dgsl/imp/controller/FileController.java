package com.dgsl.imp.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dgsl.imp.service.FileService;

@RestController
public class FileController {

	@Autowired
	FileService service;

	@CrossOrigin
	@PostMapping(value = "/uplodeFile")
	public @ResponseBody String uplodeFile(@RequestBody MultipartFile filename, @RequestParam("label") String label) {
		System.out.println("File Name :::: " + filename.getOriginalFilename());
		System.out.println("label :::: " + label);

//		try {
//			String filePath = "D:\\Test";
//			String fileLoc = filePath + File.separator + filename.getOriginalFilename();
//
//			File newFile = new File(fileLoc);
//
//			// if the directory does not exist, create it
//			if (!newFile.getParentFile().exists()) {
//				newFile.getParentFile().mkdirs();
//			}
//
//			FileCopyUtils.copy(filename.getBytes(), newFile);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}

		return service.uplodeFile(filename, label);
	}

	@CrossOrigin
	@GetMapping(value = "/pdf")
	@Produces("application/pdf")
	public javax.ws.rs.core.Response getPdf() throws Exception {
		System.out.println("asakjskjkasdkjasd");
		File file = new File("D:\\Test\\Presentation.pdf");
		FileInputStream fileInputStream = new FileInputStream(file);
		javax.ws.rs.core.Response.ResponseBuilder responseBuilder = javax.ws.rs.core.Response
				.ok((Object) fileInputStream);
		responseBuilder.type("application/pdf");
		responseBuilder.header("Content-Disposition", "attachment; filename=restfile.pdf");
		return responseBuilder.build();
	}

}
