package com.messagegcp.Messaging.App.Common.Async.Controller.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.messagegcp.Messaging.App.Common.Async.Service.Email.EmailService;

import jakarta.mail.MessagingException;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	@Value("${api.expected-key}")
	private String expectedApiKey;
	
	public EmailController(EmailService emailService) {
		this.emailService = emailService;
	}

	@PostMapping("/send-email")
	public ResponseEntity<?> sendEmail(@RequestHeader("API-Key") String apiKey,@RequestParam String to,
									   @RequestParam String subject,
									   @RequestParam String content){
		try {
			if (!apiKey.equals(expectedApiKey)) {
		        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid API Key");
		    }
			
			emailService.sendEmail(to, subject, content);
			return ResponseEntity.ok("Email sent successfully");
		} catch (MessagingException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error Sending email");
		}
	}
	
}
