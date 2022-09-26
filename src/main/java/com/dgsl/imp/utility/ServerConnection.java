package com.dgsl.imp.utility;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.security.auth.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dgsl.imp.utility.ReadPropertyFile;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

public class ServerConnection {

	private final static Logger logger = LoggerFactory.getLogger(ServerConnection.class);
	private static ResourceBundle rb = ResourceBundle.getBundle("application", Locale.US);
	static ReadPropertyFile bundle = ReadPropertyFile.getInstance();
	UserContext gUserContext = null;
	boolean isConnection = false;

	private String objectStore = "";
	private String username = "";
	private String password = "";


	public Connection getConnMisc() {

		String uri = ReadPropertyFile.getInstance().getPropConst().getProperty("filnetUrl");
		logger.info("URI  : " + uri);
		// Make connection.
		Connection conn = Factory.Connection.getConnection(uri);
		// UserContext uses Jaas call to authenticate with server
		String lStanzaName = "FileNetP8WSI";
		logger.info("StanzaName : " + lStanzaName);

		logger.info("getConnMisc :::  username :: " + username + "  |  password :: " + password);

		Subject subject = UserContext.createSubject(conn, username, password, lStanzaName);
		UserContext.get().pushSubject(subject);
		gUserContext = UserContext.get();
//		UserContext.get().popSubject();
		setConnection(true);
		logger.info("Exiting getConn()");
		return conn;
	}

	public Domain getDomainMisc() {
		// retrives an instance of domain class
		logger.info("Inside getDomain()");
		Connection lConnection = getConnMisc();
		Domain domain = Factory.Domain.fetchInstance(lConnection, null, null);
		logger.info("Domain: " + domain.get_Name());
		System.out.println("Domain: " + domain.get_Name());
		logger.info("Exiting getDomain()");
		return domain;
	}

	public ObjectStore getObjectStore(String pSolution) throws SQLException {
		// To fetch required object store by passing objstore name
		logger.info("Inside getObjectStore()");
		Domain lDomain = getDomainMisc();
		ObjectStore objStore = Factory.ObjectStore.fetchInstance(lDomain, objectStore, null);
		System.out.println("Object Store =" + objStore.get_DisplayName());
		logger.info("Object Store =" + objStore.get_DisplayName());
		logger.info("Exiting getObjectStore()");
		return objStore;
	}

	public Connection getConn() {

		String uri = ReadPropertyFile.getInstance().getPropConst().getProperty("filnetUrl");
		String username = ReadPropertyFile.getInstance().getPropConst().getProperty("filnetUsername");
		String password = ReadPropertyFile.getInstance().getPropConst().getProperty("filnetPassword");

		// Make connection.
		Connection conn = Factory.Connection.getConnection(uri);
		// UserContext uses Jaas call to authenticate with server
		String lStanzaName = "FileNetP8WSI";
		logger.info("StanzaName : " + lStanzaName);
		Subject subject = UserContext.createSubject(conn, username, password, lStanzaName);
		UserContext.get().pushSubject(subject);
		gUserContext = UserContext.get();
//		UserContext.get().popSubject();
		setConnection(true);
		logger.info("Exiting getConn()");
		return conn;
	}

	public Domain getDomain() {
		// retrives an instance of domain class
		logger.info("Inside getDomain()");
		Connection lConnection = getConn();
		Domain domain = Factory.Domain.fetchInstance(lConnection, null, null);
		logger.info("Domain: " + domain.get_Name());
		System.out.println("Domain: " + domain.get_Name());
		logger.info("Exiting getDomain()");
		return domain;
	}

	public ObjectStore getObjectStore() {
		// To fetch required object store by passing objstore name
		logger.info("Inside getObjectStore()");
		String objStoreName = ReadPropertyFile.getInstance().getPropConst().getProperty("objStoreName");
		logger.info("objStoreName : " + objStoreName);
		Domain lDomain = getDomain();
		ObjectStore objStore = Factory.ObjectStore.fetchInstance(lDomain, objStoreName, null);
		System.out.println("Object Store =" + objStore.get_DisplayName());
		logger.info("Object Store =" + objStore.get_DisplayName());
		logger.info("Exiting getObjectStore()");
		return objStore;
	}

	public void popUserContext() {
		gUserContext.popSubject();
		logger.info("Connection PopOut");
	}

	public boolean isConnection() {
		return isConnection;
	}

	public void setConnection(boolean isConnection) {
		this.isConnection = isConnection;
	}

}
