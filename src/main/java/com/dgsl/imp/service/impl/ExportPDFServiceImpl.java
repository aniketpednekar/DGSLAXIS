package com.dgsl.imp.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.dgsl.imp.entity.CardEntity;
import com.dgsl.imp.entity.Constant;
import com.dgsl.imp.service.ExportPDFService;
import com.lowagie.text.DocumentException;

@Service
public class ExportPDFServiceImpl implements ExportPDFService {

	@Override
	public ByteArrayInputStream exportReceiptPDF(String templateName, String tableStr) {
		ByteArrayInputStream byteArrayInputStream = null;
		try {
			String htmlContent = StreamUtils.copyToString(
					new ClassPathResource("templates/HTMLforPDF.html").getInputStream(), StandardCharsets.UTF_8);

			System.out.println(htmlContent);

			htmlContent = htmlContent.replace("{DATA}", getDataStringForHtml());

			htmlContent = htmlContent.replaceAll(",@", "<br />");

			tableStr = tableStr.replace("table table-hover", "ExTable");

			htmlContent = htmlContent.replace("{TABLE-DATA}", tableStr);

			System.out.println(
					"******************************************************************************************");
			System.out.println(htmlContent);

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(htmlContent);
			renderer.layout();
			renderer.createPDF(byteArrayOutputStream, false);
			renderer.finishPDF();
			byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		} catch (DocumentException | IOException | JSONException e) {
			e.printStackTrace();
		}

		return byteArrayInputStream;
	}

	public String getDataStringForHtml() throws JSONException {

		String fnlTemplateString = "";

		Map<String, List<CardEntity>> lMap = Constant.createTestData();

		List<CardEntity> cardItems = lMap.get("data");

		for (CardEntity cardEntity : cardItems) {

			String templateString = "<div><div class=\"dataClass\"><div class=\"headPoint\"><p>{HEAD}</p></div><div><p>{BODY}</p></div></div></div>";
			templateString = templateString.replace("{HEAD}", cardEntity.getHEADER());
			templateString = templateString.replace("{BODY}", cardEntity.getBODY());
			fnlTemplateString += templateString;
		}

		return fnlTemplateString;
	}

}
