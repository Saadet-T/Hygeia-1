package com.backend.hygeia.utils;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class PersonController {

	private static final Logger logger = LogManager.getLogger("PersonController");
	private String deneme;
	private String isim;
	private String email;
	private String address;
	private String phone;
	private String postCode;

	@RequestMapping("/getinfo")
	public String getInfo( HttpServletRequest request,Model model) {// İstek yapıyor istek bodysinde çalışanların listesi var.
		String username = request.getParameter("username");
		logger.info("AAAAAAAAAAAAAAAAAAAA");
		logger.info(""+ username);
	try {
			Class.forName("org.postgresql.Driver");
			
		Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hygeiaDB", "postgres", "admin");
		Statement stmt = con.createStatement();
		boolean hasMoreResults = stmt.execute("SELECT US.id AS \"id\", US.email AS \"mail\", US.password AS \"parola\", US.returnurl, US.username AS \"Username\",US.address AS \"address\",US.number AS \"phoneNumber\" ,US.post_code AS \"postCode\" FROM users US where username='"+username+"';");
//		while (hasMoreResults) {
			ResultSet rs = stmt.getResultSet();
			while(rs.next()) {
				email = rs.getString("mail");
				address = rs.getString("address");
				phone = rs.getString("phoneNumber");
				postCode = rs.getString("postCode");
				isim=rs.getString("Username");
				System.out.println(isim);
			}
			con.close();
				} catch (Exception e) {
			System.out.println(e);
		}
	model.addAttribute("deneme", deneme);
	model.addAttribute("isim", isim);
	model.addAttribute("mail", email);
	model.addAttribute("address", address);
	model.addAttribute("phone", phone);
	model.addAttribute("postCode", postCode);
	return "getinfo";
	}
	}


