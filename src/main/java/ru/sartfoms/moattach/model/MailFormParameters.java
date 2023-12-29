package ru.sartfoms.moattach.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

public class MailFormParameters {
	@Email(regexp = "^(?:.+[@].+[\\.].+)|$")
	private String from;
	private String subject;
	private MultipartFile attachment;
	private Boolean success = false;
	@NotEmpty
	private String body;
	
	public String getSubject() {
		return subject;
	}
	public String getBody() {
		return body;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public MultipartFile getAttachment() {
		return attachment;
	}
	public void setAttachment(MultipartFile attachment) {
		this.attachment = attachment;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public Boolean getSuccess() {
		return success;
	}
	public void setSuccess(Boolean success) {
		this.success = success;
	}
}
