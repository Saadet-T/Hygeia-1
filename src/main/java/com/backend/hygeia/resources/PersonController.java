package com.backend.hygeia.resources;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.hygeia.entities.User;
import com.backend.hygeia.entities.responses.MessageResponse;
import com.backend.hygeia.repositories.RoleRepository;
import com.backend.hygeia.repositories.UserRepository;
import com.backend.hygeia.security.services.UserDetailsImpl;

@Controller
public class PersonController {

	private static final Logger logger = LogManager.getLogger("PersonController");
	private String deneme;
	private String isim;

	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	RoleRepository roleRepository;

	private String email;
	private String address;
	private String phone;
	private String postCode;

	@RequestMapping("/getinfo")
	public String getInfo(HttpServletRequest request, Model model) {// İstek yapıyor istek bodysinde çalışanların 															// listesi var.
		String username = request.getParameter("username");
		
		try {
			Class.forName("org.postgresql.Driver");

			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hygeiaDB", "postgres",
					"admin");
			Statement stmt = con.createStatement();
			boolean hasMoreResults = stmt.execute(
					"SELECT US.id AS \"id\", US.email AS \"mail\", US.password AS \"parola\", US.returnurl, US.username AS \"Username\",US.address AS \"address\",US.number AS \"phoneNumber\" ,US.post_code AS \"postCode\" FROM users US where username='"
							+ username + "';");
//		while (hasMoreResults) {
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				email = rs.getString("mail");
				address = rs.getString("address");
				phone = rs.getString("phoneNumber");
				postCode = rs.getString("postCode");
				isim = rs.getString("Username");
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

	@PostMapping("/updateUserInfo")
	public ResponseEntity<MessageResponse> updateUserInfo(HttpServletRequest request, HttpServletResponse response)
			// Telefon numarası , adres , posta kodu eklenecek
			throws URISyntaxException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String returnURL = request.getParameter("returnURL");
		String number = request.getParameter("number");
		String shortAddress = request.getParameter("address");
		String postCode = request.getParameter("postCode");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
		// Create new user's account
		Optional<User> optUser = userRepository.findById(userId);
		User user = optUser.get();
		user.setUsername(username);
		user.setEmail(email);
		user.setNumber(number);
		user.setPassword(encoder.encode(password));
		user.setPostCode(postCode);
		userRepository.save(optUser.get());

		
		return ResponseEntity.ok(new MessageResponse("User update successfully!"));
	}
}
