package ru.sartfoms.moattach.service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ru.sartfoms.moattach.entity.User;

@Service
public class EmailService {
	private final JavaMailSender javaMailSender;
	private String from;

	public EmailService(JavaMailSender javaMailSender, @Value("${app.name}") String appName) {
		this.javaMailSender = javaMailSender;
		from = appName + "@sartfoms.ru";
	}

	public void sendMessage(String to, String email, String subject, String text, User user) {

		text = text + System.lineSeparator() + System.lineSeparator() + "user - " + user.getName();
		text = text + System.lineSeparator() + "ФИО - " + user.getLastname();
		text = text + System.lineSeparator() + "ЛПУ - " + user.getLpuId();
		if (!email.isEmpty()) {
			text = text + System.lineSeparator() + "email - " + email;
		}

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(text);
		mailMessage.setFrom(from);

		javaMailSender.send(mailMessage);
	}

	public void sendMessageWithAttachment(String to, String email, String subject, String text,
			MultipartFile multipartFile, User user) throws MessagingException, IOException {

		text = text + System.lineSeparator() + System.lineSeparator() + "user - " + user.getName();
		text = text + System.lineSeparator() + "ФИО - " + user.getLastname();
		text = text + System.lineSeparator() + "ЛПУ - " + user.getLpuId();
		if (!email.isEmpty()) {
			text = text + System.lineSeparator() + "email - " + email;
		}

		MimeMessage message = javaMailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setFrom(from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);

		final InputStreamSource attachment = new ByteArrayResource(multipartFile.getBytes());
		helper.addAttachment(multipartFile.getOriginalFilename(), attachment);

		javaMailSender.send(message);
	}

}
