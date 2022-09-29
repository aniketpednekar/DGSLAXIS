package com.dgsl.imp.utility;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUtility {
	
	private static ResourceBundle rb = ResourceBundle.getBundle("application", Locale.US);
	private final static Logger logger = LoggerFactory.getLogger(FileUtility.class);

	public String saveFileToFolder(MultipartFile pFile) {
		String lFileLocation = "";
		try {
			logger.info("Inside saveFileToFolder");
			String fileLoc = ReadPropertyFile.getInstance().getPropConst().getProperty("filePath")
					+ pFile.getOriginalFilename();
			logger.info("File Location : " + fileLoc);
			File newFile = new File(fileLoc);

			// if the directory does not exist, create it
			if (!newFile.getParentFile().exists()) {
				newFile.getParentFile().mkdirs();
			}

			FileCopyUtils.copy(pFile.getBytes(), newFile);
			logger.info("File Copied successfully");
			lFileLocation = fileLoc;
			
		} catch (IOException e) {
			logger.error("Exception is :: " + e.getMessage(), e);
		}
		return lFileLocation;
	}
	
	public void deleteFolder() {
		logger.info("Inside deleteFolder()");
		File directory = new File(ReadPropertyFile.getInstance().getPropConst().getProperty("filePath"));

		// make sure directory exists
		if (!directory.exists()) {
			logger.info("Directory does not exist.");
			try {

				File folderDirectory = new File(
						ReadPropertyFile.getInstance().getPropConst().getProperty("filePath"));
				delete(folderDirectory);

			} catch (IOException e) {
				e.printStackTrace();
				logger.info("Exception is=======" + e.fillInStackTrace());
			}

		} else {

			try {

				delete(directory);

			} catch (IOException e) {
				e.printStackTrace();
				try {

					File folderDirectory = new File(
							ReadPropertyFile.getInstance().getPropConst().getProperty("filePath"));
					delete(folderDirectory);

				} catch (IOException e1) {
					e1.printStackTrace();
					logger.info("Exception is=======" + e1.fillInStackTrace());
				}
			}

		}
		logger.info("Exiting deleteFolder()");
	}

	public static void delete(File file) throws IOException {

		if (file.isDirectory()) {

			// directory is empty, then delete it
			if (file.list().length == 0) {

				file.delete();
				logger.info("Directory is deleted : " + file.getAbsolutePath());

			} else {

				// list all the directory contents
				String files[] = file.list();

				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);

					// recursive delete
					delete(fileDelete);
				}

				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					logger.info("Directory is deleted : " + file.getAbsolutePath());
				}
			}

		} else {
			// if file, then delete it
			file.delete();
			logger.info("File is deleted : " + file.getAbsolutePath());
		}
	}

}
