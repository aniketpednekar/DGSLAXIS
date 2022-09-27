package com.dgsl.imp.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.filenet.api.collection.ContentElementList;
import com.filenet.api.constants.AutoClassify;
import com.filenet.api.constants.AutoUniqueName;
import com.filenet.api.constants.CheckinType;
import com.filenet.api.constants.DefineSecurityParentage;
import com.filenet.api.constants.RefreshMode;
import com.filenet.api.core.ContentTransfer;
import com.filenet.api.core.Document;
import com.filenet.api.core.Factory;
import com.filenet.api.core.Folder;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.core.ReferentialContainmentRelationship;
import com.filenet.api.property.Properties;

public class SaveDocument {

	private final static Logger logger = LoggerFactory.getLogger(SaveDocument.class);

	public String createCaseWithDocument(ObjectStore objStr, ArrayList<String> pFileLocation, JSONObject pJson) {
		Document doc = null;
//		Connection connection = null;
		String fileName = null;
		String lResponse = "Success";
		try {
			logger.info("Inside createCaseWithDocument()");
//			int counter = 0;
//			DBConnection lDbConnection = new DBConnection();
//			connection = lDbConnection.getDBConnection();
//			logger.info("Calling insert()");
//			connection.setAutoCommit(false);
//			CommonUtility.insertUniqReferenceNo(pJson, connection);
//			ArrayList<Id> newDocIds = new ArrayList<Id>();
			for (String lFileLocation : pFileLocation) {
				// Get Folder
				// Folder folder = null;
				logger.info("File Location :::: " + lFileLocation);

				logger.info("File size :::: " + pFileLocation.size());
//				String documentFolderNameInObjectStore = "\\" + ReadPropertyFile.getInstance().getContantProperty()
//						.getProperty("documentFolderNameInObjectStore");
//				folder = Factory.Folder.fetchInstance(objStr, documentFolderNameInObjectStore, null);
//				logger.info("documentFolderNameInObjectStore :::: " + documentFolderNameInObjectStore);

				// gets a file from local system to upload
				File files = new File(lFileLocation);

				FileInputStream fileInput = new FileInputStream(files);

				fileName = files.getName();
				logger.info("File Name ::::" + fileName);

				String docClass = "";
//				String docClass = ReadPropertyFile.getInstance().getConstantProperty().getProperty("docClassName");
				logger.info("documentClassName :::: " + docClass);

				doc = Factory.Document.createInstance(objStr, docClass);
				logger.info("Document created");

				String mimeType = "application/docs";
				String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
				logger.info("Document extension :::: " + extension);

				if (extension != null && extension != "") {
					if (ReadPropertyFile.getInstance().getMIMEProp().getProperty(extension.toUpperCase()) != null) {
						mimeType = ReadPropertyFile.getInstance().getMIMEProp().getProperty(extension.toUpperCase());
					}
				}
				logger.info("Document mimeType :::: " + mimeType);

				if (fileInput != null) {
					ContentTransfer contentTransfer = Factory.ContentTransfer.createInstance();
					contentTransfer.setCaptureSource(fileInput);
					ContentElementList contentElementList = Factory.ContentElement.createList();
					contentElementList.add(contentTransfer);
					doc.set_ContentElements(contentElementList);
					contentTransfer.set_RetrievalName(fileName);
					doc.set_MimeType(mimeType);
				}
				// Check-in the doc
				doc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
//				logger.info("Assigning Property");
				Properties lProperties = doc.getProperties();
//				pJson.remove("Document");
//				setPropertyToDocument(pJson, lProperties);
				lProperties.putValue("DocumentTitle", fileName);

//				counter++;
//
//				if (counter == pFileLocation.size()) {
//					logger.info("*** Launch Case");
//					logger.info(ReadPropertyFile.getInstance().getConstantProperty().getProperty("launchCaseKeys")
//							+ " :::: "
//							+ ReadPropertyFile.getInstance().getConstantProperty().getProperty("launchCaseValue"));
//					lProperties.putValue(
//							ReadPropertyFile.getInstance().getConstantProperty().getProperty("launchCaseKeys"),
//							ReadPropertyFile.getInstance().getConstantProperty().getProperty("launchCaseValue"));
//				} else {
//					logger.info("*** Launch Case");
//					logger.info(ReadPropertyFile.getInstance().getConstantProperty().getProperty("launchCaseKeys")
//							+ " :::: "
//							+ ReadPropertyFile.getInstance().getConstantProperty().getProperty("launchCaseNegValue"));
//					lProperties.putValue(
//							ReadPropertyFile.getInstance().getConstantProperty().getProperty("launchCaseKeys"),
//							ReadPropertyFile.getInstance().getConstantProperty().getProperty("launchCaseNegValue"));
//				}

				doc.save(RefreshMode.REFRESH);
				doc.save(null);

//				if (counter != pFileLocation.size()) {
//					newDocIds.add(doc.get_Id());
//				}
				System.out.println(doc.get_Id());
				logger.info("doc task created");
			}

//			connection.setAutoCommit(true);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception is :::: " + e.getMessage(), e);
			lResponse = e.getMessage();
		}
		logger.info("Document Uploaded Successfully");
		return lResponse;
	}

//	public ArrayList<String> saveDocToFolder(MultipartFile pFile, JSONObject pJson) throws Exception {
//		ArrayList<String> lFileLocation = new ArrayList<String>();
//		try {
//			logger.info("File saving To Folder");
//			if (pJson.has("documentsUploadedOnSFTP")) {
//				if (pJson.getString("documentsUploadedOnSFTP").equalsIgnoreCase("Y")) {
//					lFileLocation = UnikenUtility.uploadExternalDoc(pJson);
//				} else {
//					lFileLocation = CommonUtility.uploadInternalDoc(pFile, pJson);
//				}
//			} else {
//				lFileLocation = CommonUtility.uploadInternalDoc(pFile, pJson);
//			}
//
//		} catch (IOException | JSONException e) {
//			// logger.error("Exception is :::: " + e.getMessage(), e);
//			throw (e);
//		}
//		return lFileLocation;
//	}

	
	//////////////////////////
	
	public ArrayList<String> saveFileToFolder(MultipartFile pFile) {
		ArrayList<String> lFileLocation = new ArrayList<String>();
		try {
//			logger.info(arcMapId + "Inside saveFileToFolder");
			System.out.println("Inside saveFileToFolder");
			String fileLoc = ReadPropertyFile.getInstance().getPropConst().getProperty("documentPath")
					+ pFile.getOriginalFilename();
//			logger.info(arcMapId + "File Location : " + fileLoc);
//			System.out.println(arcMapId + "File Location : " + fileLoc);
			File newFile = new File(fileLoc);

			// if the directory does not exist, create it
			if (!newFile.getParentFile().exists()) {
				newFile.getParentFile().mkdirs();
			}

			FileCopyUtils.copy(pFile.getBytes(), newFile);
//			logger.info(arcMapId + "File Copied successfully");
			System.out.println("File Copied successfully");
			lFileLocation.add(fileLoc);

			String extension = pFile.getOriginalFilename().substring(pFile.getOriginalFilename().lastIndexOf(".") + 1);
			// modified for MIME property file chngs
//			Constant.setExtention(extension);

			// ARC FIDB_TATASTEEL Changes for upload zip and unzip file
//			if ("FIDB_TATASTEEL".equalsIgnoreCase(Constant.getProduct()) || "FIBLC".equalsIgnoreCase(Constant.getProduct())) {
//				if (extension.equalsIgnoreCase("zip")) {
//					ArrayList<String> lFileLocation2 = unzip(fileLoc,
//							ReadPropertyFile.getInstance().getPropConst().getProperty("documentPath"));
//					for (String path : lFileLocation2) {
//						lFileLocation.add(path);
//					}
//					logger.info("All files with ZIP File Copied successfully" + lFileLocation);
//				}
//			} else {
//				if (extension.equalsIgnoreCase("zip")) {
//					lFileLocation = unzip(fileLoc,
//							ReadPropertyFile.getInstance().getPropConst().getProperty("documentPath"));
//				}
//
//			}
		} catch (IOException e) {
//			logger.error("Exception is :::: " + arcMapId + e.getMessage(), e);
			e.printStackTrace();
		}
		return lFileLocation;
	}
	
	public String saveDocument(ObjectStore objStr, ArrayList<String> pFileLocation, JSONObject pJson) {
		Document doc = null;
		String lResponse = "Fail";
		Connection connection = null;
		try {
			logger.info("Inside createCaseWithDocument()");
			boolean flag = true;
//			DbConnection lDbConnection = new DbConnection();
//			connection = lDbConnection.getInstance();
//			connection.setAutoCommit(false);
//			logger.info("Calling insert()");
//			insertReferenceNo(pJson, connection);
			for (String lFileLocation : pFileLocation) {
				// Get Folder
				Folder folder = null;
				logger.info("FileLocation : " + lFileLocation);
				String documentFolderNameInObjectStore = "\\"
						+ ReadPropertyFile.getInstance().getPropConst().getProperty("documentFolderNameInObjectStore");
				folder = Factory.Folder.fetchInstance(objStr, documentFolderNameInObjectStore, null);
				logger.info("documentFolderNameInObjectStore : " + documentFolderNameInObjectStore);
				// gets a file from local system to upload
				File files = new File(lFileLocation);

				FileInputStream fileInput = new FileInputStream(files);

				String fileName = files.getName();
				System.out.println("fileName......" + fileName);

//				String docClass;
//				if (pJson.getString("isTradeTran").equalsIgnoreCase("yes"))
//					docClass = ReadPropertyFile.getInstance().getPropConst().getProperty("tradeClassName");
//				else
//					docClass = ReadPropertyFile.getInstance().getPropConst().getProperty("retailClassName");
				// Create Document
				String docClass = ReadPropertyFile.getInstance().getPropConst().getProperty("documentClassName");
				logger.info("documentClassName : " + docClass);
				doc = Factory.Document.createInstance(objStr, docClass);
				// doc.save(RefreshMode.REFRESH);
				System.out.println("Document created.........");
				System.out.println("File is" + fileInput);

				if (fileInput != null) {
					ContentTransfer contentTransfer = Factory.ContentTransfer.createInstance();
					contentTransfer.setCaptureSource(fileInput);
					ContentElementList contentElementList = Factory.ContentElement.createList();
					contentElementList.add(contentTransfer);
					doc.set_ContentElements(contentElementList);
					contentTransfer.set_RetrievalName(fileName);
					doc.set_MimeType("application/docs");

				}

				// Check-in the doc
				doc.checkin(AutoClassify.DO_NOT_AUTO_CLASSIFY, CheckinType.MAJOR_VERSION);
				String documentName = fileName;
				logger.info("Assigning Property");
				Properties lProperties = doc.getProperties();
				lProperties.putValue("DocumentTitle", fileName);
//				String response = setTradeIRMRetailIRMPropertyToDocument(pJson, lProperties);
//				logger.info("response=======" + response);
//				if (response.equalsIgnoreCase("Success")) {
//					logger.info("Inside Success response=======" + response);
//					lProperties.putValue("DocumentTitle", fileName);
//					lProperties.putValue("COMMN_QueueID", pJson.getString("arcMapId"));
//					lProperties.putValue("COMMN_Source", "ARC");
////					lProperties.putValue("COMMN_SOLID", "010");
//					lProperties.putValue(ReadPropertyFile.getInstance().getPropConst().getProperty("operation"),
//							ReadPropertyFile.getInstance().getPropConst().getProperty("operationValue"));
//					logger.info("Operation : "
//							+ ReadPropertyFile.getInstance().getPropConst().getProperty("operationValue"));
//					if (flag) {
//						logger.info("launchCaseValue "
//								+ ReadPropertyFile.getInstance().getPropConst().getProperty("launchCaseValue"));
//						lProperties.putValue("COMMN_LaunchCase",
//								ReadPropertyFile.getInstance().getPropConst().getProperty("launchCaseValue"));
//						flag = false;
//					} else
//						lProperties.putValue("COMMN_LaunchCase", "No");
					// Get and put the doc properties
					doc.save(RefreshMode.REFRESH);

					// Stores above content to the folder

					ReferentialContainmentRelationship rc = folder.file(doc, AutoUniqueName.AUTO_UNIQUE, documentName,
							DefineSecurityParentage.DO_NOT_DEFINE_SECURITY_PARENTAGE);
					rc.save(RefreshMode.REFRESH);
					connection.setAutoCommit(true);
					System.out.println("doc task created......");
					logger.info("doc task created......");
					System.out.println(doc.get_Id());
					lResponse = "Success";
				} 

			
			logger.info("Exiting createCaseWithDocument()");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
			e.printStackTrace();
			logger.info(e.toString());
			lResponse = e.getMessage();
		}

		System.out.println("Document Successful");
		return lResponse;
	}
}
