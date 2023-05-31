package com.backend.hygeia.resources;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.hygeia.entities.ERole;
import com.backend.hygeia.entities.Role;
import com.backend.hygeia.entities.User;
import com.backend.hygeia.entities.responses.MessageResponse;
import com.backend.hygeia.repositories.RoleRepository;
import com.backend.hygeia.repositories.UserRepository;
import com.backend.hygeia.security.jwt.JwtUtils;
import com.backend.hygeia.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	private static final Logger logger = LogManager.getLogger("AuthController");

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    logger.trace("Entering method processOrder().");
	    logger.debug("Received order with ID 12345.");
	    logger.info("Order shipped successfully.");
	    logger.warn("Potential security vulnerability detected in user input: '...'");
	    logger.error("Failed to process order. Error: {. . .}");
	    logger.fatal("System crashed. Shutting down...");
	    logger.info("Giriş yapan kullanıcı",username); 
	    try {
	        Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(username, password));

	        String jwt = jwtUtils.generateJwtToken(authentication);
//	        logger.
	        logger.debug("Debug log message");
	        logger.info("Info log message");
	        logger.error("Error log message");
	        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
	        List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
	                .collect(Collectors.toList());

	        Cookie cookie = new Cookie("JWT", jwt);
	        cookie.setHttpOnly(false);
	        cookie.setSecure(false);
	        cookie.setPath("/");
	        cookie.setMaxAge(3600); // 1 saat geçerli olacak şekilde ayarlandı
	        response.addCookie(cookie);
	        //Responsda Headre a jwt yi ekler bunun kullanımı biraz karışık do internal filterdaki yorum satırındaki ParseJWT operasyonunun kulllanır
	        //response.addHeader("Authorization","Bearer "+jwt );

	        return ResponseEntity.status(HttpStatus.FOUND)
	                .header(HttpHeaders.LOCATION, "/")
	                .body(null);
	        
	    } catch (AuthenticationException e) {
	        // Kullanıcı adı veya şifre hatalı olduğunda buraya düşer
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).header(HttpHeaders.LOCATION, "/login").body(null);
	    }
	}


	@PostMapping("/signup")
	public ResponseEntity<MessageResponse> getLogin(HttpServletRequest request, HttpServletResponse response)
	//Telefon numarası , adres , posta kodu eklenecek
			throws URISyntaxException, IOException {
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String returnURL = request.getParameter("returnURL");
		String number = request.getParameter("number");
		String shortAddress = request.getParameter("address");
		String postCode = request.getParameter("postCode");
		

		if (userRepository.existsByUsername(request.getParameter("username"))) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(request.getParameter("email"))) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(request.getParameter("username"), request.getParameter("email"),
				encoder.encode(request.getParameter("password")), request.getParameter("returnURL"),request.getParameter("number"),
				request.getParameter("address"),request.getParameter("postCode"));

		Set<String> strRoles = new HashSet<>();
		Set<Role> roles = new HashSet<>();
		
		if (request.getParameter("Role") == null) {
			strRoles.add("user");
		} else {
			strRoles.add(request.getParameter("Role"));
		}

		if (strRoles.isEmpty()) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found." + ERole.ROLE_USER));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		response.sendRedirect(returnURL);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}
