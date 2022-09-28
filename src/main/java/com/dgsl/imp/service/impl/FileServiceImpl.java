package com.dgsl.imp.service.impl;

import java.io.File;
import java.io.FileInputStream;

import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dgsl.imp.service.FileService;
import com.dgsl.imp.utility.ReadPropertyFile;
import com.dgsl.imp.utility.ServerConnection;
import com.filenet.api.collection.ContentElementList;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.ClassNames;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.ReferentialContainmentRelationship;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uplodeFile(MultipartFile file, String label) {

		try {
			ServerConnection lServerConnection = new ServerConnection();
			ObjectStore objStore = lServerConnection.getObjectStore("-");

			String lFilePath = "H:\\Test\\";

			String lFileName = file.getOriginalFilename();

			String lFileLocation = lFilePath + lFileName;

			// Get the display name of the object store.
			String lObjStoreName = objStore.get_DisplayName();

			System.out.println("Object store name = " + lObjStoreName);

			// String docClass =
			// ReadPropertyFile.getInstance().getConstantProperty().getProperty("docClassName");
			// logger.info("documentClassName :::: " + docClass);

			// Create a document instance.
			Document doc = Factory.Document.createInstance(objStore, ClassNames.DOCUMENT);

			// Set document properties.

//			doc.save(RefreshMode.NO_REFRESH);

//			*****Document Upload from Local Code Start*******	

			File newFile = new File(lFileLocation);

			if (!newFile.getParentFile().exists()) {
				newFile.getParentFile().mkdirs();
			}

			FileCopyUtils.copy(file.getBytes(), newFile);
//			logger.info("File Copied successfully");

			File files = new File(lFileLocation);

			lFileName = files.getName();

			FileInputStream fileInput = new FileInputStream(files);

			String mimeType = "application/docs";
			String extension = lFileName.substring(lFileName.lastIndexOf(".") + 1);
//			logger.info("Document extension :::: " + extension);

			if (extension != null && extension != "") {
				if (ReadPropertyFile.getInstance().getMIMEProp().getProperty(extension.toUpperCase()) != null) {
					mimeType = ReadPropertyFile.getInstance().getMIMEProp().getProperty(extension.toUpperCase());
				}
			}

//			String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
			doc.getProperties().putValue("DocumentTitle", lFileName);
			doc.set_MimeType(mimeType); // if its your pdf then set mimetype for PDF

			if (fileInput != null) {
				ContentTransfer contentTransfer = Factory.ContentTransfer.createInstance();
				contentTransfer.setCaptureSource(fileInput);
				ContentElementList contentElementList = Factory.ContentElement.createList();
				contentElementList.add(contentTransfer);
				doc.set_ContentElements(contentElementList);
				contentTransfer.set_RetrievalName(lFileName);
				doc.set_MimeType(mimeType);
			}

			// Check in the document.
			doc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);

			doc.save(RefreshMode.NO_REFRESH);

			// File the document.
			Folder folder = Factory.Folder.fetchInstance(objStore, "\\ARC", null);
//			ReferentialContainmentRelationship rcr = folder.file(doc, AutoUniqueName.AUTO_UNIQUE, "Test.txt",
//					DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
//			rcr.save(RefreshMode.NO_REFRESH);

			ReferentialContainmentRelationship rcr = folder.file(doc, AutoUniqueName.AUTO_UNIQUE, lFileName,
					DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
			rcr.save(RefreshMode.REFRESH);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "success";
	}

}