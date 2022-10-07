package com.dgsl.dwp.service;

import java.util.Map;

import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.dgsl.dwp.bean.DwpAclEmailTemplate;
import com.dgsl.dwp.repository.DwpEmailTemplateRepository;
import com.dgsl.imp.entity.Constant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DwpAclEmailerService {

	@Autowired
	DwpEmailTemplateRepository dwpEmailTemplateRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	public DwpAclEmailTemplate getEmailTemplate(String toStep, String fromStep, String action) {
		return dwpEmailTemplateRepository.getEmailTemplate(toStep, fromStep, action);
	}

	public String sendMail(DwpAclEmailTemplate pDwpAclEmailTemplate) {
		System.out.println("In sendMail");

		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			System.out.println("Sender : " + pDwpAclEmailTemplate.getFromMail());

			mailMessage.setFrom(pDwpAclEmailTemplate.getFromMail());
			mailMessage.setTo(pDwpAclEmailTemplate.getToMail());
			mailMessage.setCc(pDwpAclEmailTemplate.getCc());
			mailMessage.setText(createMailBody(pDwpAclEmailTemplate.getBody()));
			mailMessage.setSubject(pDwpAclEmailTemplate.getSubject());

			System.out.println("Sending Mail");
			javaMailSender.send(mailMessage);

		} catch (MailException e) {
			e.printStackTrace();

		}
		return "Mail Sent Successfully...";

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
