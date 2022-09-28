package com.dgsl.imp.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ReadPropertyFile {

	@Autowired
	ObjectFactory<HttpSession> httpSessionFactory;

	final static Logger logger = LoggerFactory.getLogger(ReadPropertyFile.class.getName());

	private static ReadPropertyFile obj = null;

	private ReadPropertyFile() {
	}

	// Now we are providing global point of access.
	public static ReadPropertyFile getInstance() {
		if (obj == null) {
			obj = new ReadPropertyFile();
			System.out.println("Getting Values from Properties file");
		}
		return obj;

	}

	public Properties getPropConst() {
		Properties prop = new Properties();
		InputStream input = null;
		String lProduct = "";
		try {
//			if(!Constant.getProduct().equals(""))
//			lProduct = Constant.getProduct()+"_";
//			input = new FileInputStream( Constant.getLocation() + lProduct+ "ARCProps.properties");
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}

	public Properties getArcArgoPropConst() {
		Properties prop = new Properties();
		InputStream input = null;

		try {

			// input = new FileInputStream("D:\\config\\argoPropConstant.properties");

			input = new FileInputStream("/was/IBM/TradAx_Props/Config/ARC/argoPropConstant.properties");
			// input = new
			// FileInputStream("/was/IBM/TradAx_Props/Config/TradePortal.properties");

			prop.load(input);

		} catch (IOException ex) {
			logger.info("Caught Exception is : Error : " + ex);
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return prop;
	}

	// modified for MIME property file chngs
	public Properties getMIMEProp() {
		Properties prop = new Properties();
		InputStream stream = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			stream = loader.getResourceAsStream("mimeType.properties");
			// stream=this.getClass().getClassLoader().getResourceAsStream("mimeType.properties");
			prop.load(stream);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return prop;
	}
}
