package com.dgsl.imp.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dgsl.imp.entity.Constant;
import com.dgsl.imp.service.ExportPDFService;

@Controller
public class HomeController {

	@Autowired
	ExportPDFService service;

	@CrossOrigin
	@RequestMapping("/")
	public ModelAndView index(Map<String, Object> model) {
		System.out.println("In Index");
		return new ModelAndView("index", model);
	}

	@CrossOrigin
	@PostMapping(value = "/getModalData")
	public @ResponseBody String getModalData() {
		System.out.println("in getModalData.");
		return Constant.getData().toString();
	}

	@CrossOrigin
	@PostMapping(value = "/saveModalData")
	public @ResponseBody String saveModalData(@RequestBody String data) throws JSONException {
		System.out.println("in saveModalData :::: " + data);
		Constant.setData(new JSONObject(data));
		return "success";
	}

	@RequestMapping("/downloadPdf")
	public void downloadPdf(@RequestParam("table1") String tableStr, HttpServletResponse response)
			throws IOException, JSONException {

		ByteArrayInputStream exportedData = service.exportReceiptPDF("HTMLforPDF", tableStr);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=receipt.pdf");
		IOUtils.copy(exportedData, response.getOutputStream());

	}

}
