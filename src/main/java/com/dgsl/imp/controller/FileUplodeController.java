package com.dgsl.imp.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dgsl.imp.service.ExportPDFService;

@Controller
public class FileUplodeController {

	@Autowired
	ExportPDFService service;

	@CrossOrigin
	@PostMapping(value = "/uplodeFile")
	public @ResponseBody String uplodeFile(@RequestBody MultipartFile filename, @RequestParam("label") String label) {
		System.out.println("File Name :::: " + filename.getOriginalFilename());
		System.out.println("label :::: " + label);

		try {
			String filePath = "D:\\Test";
			String fileLoc = filePath + File.separator + filename.getOriginalFilename();

			File newFile = new File(fileLoc);

			// if the directory does not exist, create it
			if (!newFile.getParentFile().exists()) {
				newFile.getParentFile().mkdirs();
			}

			FileCopyUtils.copy(filename.getBytes(), newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Success";
	}

}
