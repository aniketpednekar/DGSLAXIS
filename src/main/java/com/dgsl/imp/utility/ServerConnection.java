package com.dgsl.imp.utility;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.security.auth.Subject;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;

public class ServerConnection {

//	private final static Logger logger = LoggerFactory.getLogger(ServerConnection.class);
// static ReadPropertyFile bundle = ReadPropertyFile.getInstance();
	UserContext lUser_Context = null;
	boolean isConnection = false;

	private String lObject_Store = "TOS";
	private String lFileNet_URL = "http://10.254.9.59:9080/wsi/FNCEWS40MTOM/";
	private String lUser_Name = "p8admin";
	private String lPassword = "Password123";

	public ObjectStore getObjectStore() throws Exception {

		// logger.info("Getting Object Store");

		// getTOSDetails(pSolution);

		Domain lDomain = getDomain();

		ObjectStore objStore = Factory.ObjectStore.fetchInstance(lDomain, lObject_Store, null);

		// logger.info("Object Store :::: " + objStore.get_DisplayName());

		// logger.info("Successfully obtained a Object Store");

		return objStore;
	}

	public Domain getDomain() throws UnsupportedEncodingException {

		// logger.info("Getting Domain");

		Connection lConnection = getConnection();

		Domain domain = Factory.Domain.fetchInstance(lConnection, null, null);

		// logger.info("Domain Name :::: " + domain.get_Name());

		// logger.info("Successfully obtained a domain");

		return domain;

	}

	public Connection getConnection() throws UnsupportedEncodingException {

		// logger.info("Getting Filenet Connection");

		// logger.info("FileNet URL :::: " + lFileNet_URL);

		// logger.info("User Name :::: " + lUser_Name + " | Password :::: " +
		// lPassword);

		Connection lConnection = Factory.Connection.getConnection(lFileNet_URL);

		String lStanzaName = "FileNetP8WSI";

		Subject lSubject = UserContext.createSubject(lConnection, lUser_Name, lPassword, lStanzaName);

		UserContext.get().pushSubject(lSubject);

		lUser_Context = UserContext.get();

		setConnection(true);

		// logger.info("The FileNet connection was established successfully.");

		return lConnection;
	}

	public void popUserContext() {
		lUser_Context.popSubject();
		// logger.info("Connection PopOut");
	}

	public boolean isConnection() {
		return isConnection;
	}

	public void setConnection(boolean isConnection) {
		this.isConnection = isConnection;
	}

}
