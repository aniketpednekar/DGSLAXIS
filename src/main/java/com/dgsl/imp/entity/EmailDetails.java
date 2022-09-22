package com.dgsl.imp.entity;


public class EmailDetails {
	private String recipient;
	private String msgBody;
	private String subject;
	private String transactionId;
	private String toStep;
	private String fromStep;
	private String action;
	private String product;	
	
	public EmailDetails(String recipient, String msgBody, String subject) {
		this.recipient = recipient;
		this.msgBody = msgBody;
		this.subject = subject;
	}

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getToStep() {
		return toStep;
	}

	public void setToStep(String toStep) {
		this.toStep = toStep;
	}

	public String getFromStep() {
		return fromStep;
	}

	public void setFromStep(String fromStep) {
		this.fromStep = fromStep;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "EmailDetails [recipient=" + recipient + ", msgBody=" + msgBody + ", subject=" + subject
				+ ", transactionId=" + transactionId + ", toStep=" + toStep + ", fromStep=" + fromStep + ", action="
				+ action + ", product=" + product + "]";
	}
	
	
	
}
