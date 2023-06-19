package com.backend.hygeia.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Controller
public class ContactUsController {
	static {
		System.setProperty("com.sun.jndi.ldap.object.trustURLCodebase", "true");
		
	}
	private static final Logger logger = LogManager.getLogger("ContactUsController");
	
	@RequestMapping("/MailSend")
	public String getLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam("file") MultipartFile file, Model model){
        final String username = "saadetelif@outlook.com.tr";
        final String password = "HYGIEA1234deneme";
		String Email = request.getParameter("email");
		String PhoneNumber = request.getParameter("phonenumber");
		String Mesaj = request.getParameter("message");
		String Konu = request.getParameter("subject");
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp-mail.outlook.com");
		props.put("mail.smtp.port", "587");
		 String fileName = file.getOriginalFilename();
		
		  if (!file.isEmpty()) {
	            try {
	                if (fileName.endsWith(".php") || fileName.endsWith(".html") || fileName.endsWith(".java") || fileName.endsWith(".jsp")) {
	                	fileName = fileName.substring(0, fileName.lastIndexOf("."));
	                	 String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\recievedImages\\"; // Yükleme dizini
	            
	 	                file.transferTo(new File(uploadPath + fileName));
	                }else {
	                	 String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\recievedImages\\"; // Yükleme dizini
		 	                file.transferTo(new File(uploadPath + fileName));	
	                }

	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        } 

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("saadetelif@outlook.com.tr"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("saadetelif@outlook.com.tr"));
			message.setSubject("Konu");
			message.setText("Konu:" + Konu + "\n\nMail Adresi:" + Email + "\n\n Telefon Numarası:" + PhoneNumber
					+ "\n\n Mesaj:" + Mesaj);

			model.addAttribute("Email", Email);
			model.addAttribute("PhoneNumber", PhoneNumber);
			model.addAttribute("Mesaj", Mesaj);
			model.addAttribute("Konu", Konu);
			model.addAttribute("ImgName",fileName);
			Transport.send(message);

			System.out.println("Mail gönderildi.");
			return "mailSended";

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	

}
