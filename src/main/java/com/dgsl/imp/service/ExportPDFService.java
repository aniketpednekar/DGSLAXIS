package com.dgsl.imp.service;

import java.io.ByteArrayInputStream;

public interface ExportPDFService {
	ByteArrayInputStream exportReceiptPDF(String templateName, String tableStr);
}