package com.dgsl.dwp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dgsl.dwp.bean.DwpAclEmailTemplate;
import com.dgsl.dwp.service.DwpAclEmailerService;

@RestController
@RequestMapping("/dwp")
public class DwpAclEmailController {
	
	@Autowired
	DwpAclEmailerService dwpAclEmailerService;
	
	@PostMapping("/sendEmail")
	public String sendMail(@RequestBody DwpAclEmailTemplate dwpAclEmailTemplate) {

		System.out.println("In sendMail");
		DwpAclEmailTemplate lDwpAclEmailTemplate = dwpAclEmailerService.getEmailTemplate(dwpAclEmailTemplate.getToStage(), dwpAclEmailTemplate.getFromStage(), dwpAclEmailTemplate.getAction());
		System.out.println(lDwpAclEmailTemplate);
		String status = dwpAclEmailerService.sendMail(lDwpAclEmailTemplate);

		return status;
	}
	
	

}
