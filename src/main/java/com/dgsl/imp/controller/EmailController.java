package com.dgsl.imp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.dgsl.imp.entity.Constant;
import com.dgsl.imp.entity.EmailDetails;
import com.dgsl.imp.service.EmailService;

@Controller
public class EmailController {

	@Autowired
	private EmailService emailService;

	// Sending a simple Email
	@PostMapping("/sendMail")
	public String sendMail(@RequestBody EmailDetails details) {

		System.out.println("In sendMail");
		details = Constant.getEmailData(details);
		System.out.println(details.toString());
		String status = emailService.sendMail(details);

		return status;
	}

}