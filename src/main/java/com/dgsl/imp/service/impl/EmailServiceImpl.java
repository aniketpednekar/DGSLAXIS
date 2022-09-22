package com.dgsl.imp.service.impl;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.dgsl.imp.entity.Constant;
import com.dgsl.imp.entity.EmailDetails;
import com.dgsl.imp.service.EmailService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	@Override
	public String sendMail(EmailDetails details) {
		System.out.println("In sendMail");
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			System.out.println("Sender : " + sender);

			mailMessage.setFrom(sender);
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(createMailBody(details.getMsgBody()));
			mailMessage.setSubject(details.getSubject());

			System.out.println("Sending Mail");
			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		} catch (Exception e) {
			e.printStackTrace();
			return "Error while Sending Mail";
		}
	}

	public String createMailBody(String mailBodyTemplate) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> valuesMap = mapper.convertValue(Constant.getBodyEnity(),
				new TypeReference<Map<String, Object>>() {
				});

		System.out.println("Map : " + valuesMap);
		StringSubstitutor sub = new StringSubstitutor(valuesMap);

		String bodyStr = sub.replace(mailBodyTemplate);

		System.out.println("Body : " + bodyStr);
		return bodyStr;
	}

}