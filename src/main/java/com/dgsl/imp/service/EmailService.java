package com.dgsl.imp.service;

import com.dgsl.imp.entity.EmailDetails;

public interface EmailService {

	String sendMail(EmailDetails details);
}
