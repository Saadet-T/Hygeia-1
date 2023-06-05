package com.backend.hygeia.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.backend.hygeia.entities.Category;
import com.backend.hygeia.entities.Notice;
import com.backend.hygeia.entities.Product;
import com.backend.hygeia.entities.User;
import com.backend.hygeia.entities.UserProduct;
import com.backend.hygeia.entities.address.City;
import com.backend.hygeia.entities.address.District;
import com.backend.hygeia.entities.address.Neighborhood;
import com.backend.hygeia.entities.responses.MessageResponse;
import com.backend.hygeia.repositories.ProductRepository;
import com.backend.hygeia.repositories.UserRepository;
import com.backend.hygeia.security.services.UserDetailsImpl;
import com.backend.hygeia.services.CategoryService;
import com.backend.hygeia.services.NoticeService;
import com.backend.hygeia.services.ProductService;
import com.backend.hygeia.services.UserProductService;
import com.backend.hygeia.services.address.CityService;
import com.backend.hygeia.services.address.DistrictService;
import com.backend.hygeia.services.address.NeighborhoodService;
import com.backend.hygeia.utils.UserProductMapper;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class HomePageController {

	@Autowired
	ProductService productService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	NoticeService noticeService;
	
	@Autowired
	UserProductService userProductService;
	
	@Autowired
	CityService cityService;
	
	@Autowired
	DistrictService districtService;
	
	@Autowired
	NeighborhoodService neighborhoodService;
	
	
	@Autowired
	UserRepository userRepository;
	 static Logger logger = LogManager.getLogger(HomePageController.class.getName());
	@GetMapping("/")
	String getProducts(Model model) {
		Gson gson = new Gson();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
			Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
			Optional<User> optUser = userRepository.findById(userId);
			optUser.get().setPassword("");
			 model.addAttribute("user", optUser.get());
		}

        Map<Product,String> productList = productService.getAllProductsWithJson();
        List<Category> categoryList = categoryService.getAllCategories();
        List<Notice> noticeList = noticeService.getAllNotices();
        List<UserProduct> userProductList = userProductService.getCurrentUsersProducts();
        String userProductListJSON = gson.toJson(userProductList);
        model.addAttribute("productList", productList);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("noticeList", noticeList);
        model.addAttribute("userProductList", userProductList);
        model.addAttribute("userProductListJSON", userProductListJSON);
        System.setProperty("log4j.configuration", "log4j.properties");


     
        //Return edilen isim sayfanın ismidir index.html ye götürür buraya gelen istekleri
        return "index";
	}
	@GetMapping("/login")
	String login() {

        return "login";
	}
	@GetMapping("/access.log")
	String logs() {
		   // Log messages
        return "application";
	}
	
	@GetMapping("/OpenRedirectForm")
	String getProducts() {
        return "OpenRedirectForm";
	}
	@GetMapping("/register")
	String register() {
        return "register";
	}
	@GetMapping("/OpenRedirect")
	String getProdu() {
        return "OpenRedirect";
	}
	@GetMapping("/ContactUS")
	String contactus() {
		return "ContactUs";
	}
	@GetMapping("/secret")
	String secret() {
		return "401";
	}
	@GetMapping("/secret/creditcards")
	String cardInfo() {
		return "cardinfo";
	}
	@GetMapping("/forgetPasswd")
	String forgetPasswd() {
		return "ForgetPasswd";
	}
	@GetMapping("/orderCompleated")
	String orderCompleated() {
		return "orderCompleated";
	}
	
	@RequestMapping("/ForgetPassword")
	public String fogetPassword(HttpServletRequest request, Model model){

		String mail = request.getParameter("mail");
		String code = request.getParameter("code");
		
			System.out.println(""+mail);

			 final String username = "saadetelif@outlook.com.tr";
		        final String password = "Superisi123";
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", "smtp-mail.outlook.com");
				props.put("mail.smtp.port", "587");

				Session session = Session.getInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

				try {

					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress("saadetelif@outlook.com.tr"));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(""+mail));
					message.setSubject("Parola sıfırlama kodu:");
					message.setText("Merhaba parola sıfırlama için kodunuz : "+code +"\n\n İyi günler dileriz.");


					Transport.send(message);
					System.out.println("Mail gönderildi.");

				} catch (MessagingException e) {
					throw new RuntimeException(e);
				}
				model.addAttribute("mail", mail);
				model.addAttribute("code", code);
	return "RenewPasswd";
	}
	
	//or hasIpAddress('192.168.0.100') or hasIpAddress('194.27.196.131')
//	@PreAuthorize("hasRole('ROLE_ADMIN') ")
	@GetMapping("/administration")
	String adminUsers(Model model) {
		List<User> userList = userRepository.findAll();
		model.addAttribute("userList", userList);
		return "admin_users";
	}
	
	@GetMapping("/updateUserInfo")
	String updateUserInfo(Model model) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
		// Create new user's account
		Optional<User> optUser = userRepository.findById(userId);
		 model.addAttribute("user", optUser.get());
		return "updateUserInfo";
	}
	
	@PostMapping("/checkout")
	public String checkout(HttpServletRequest request,Model model) {
		//alt kısımdaki userId kısmı frontendden geliyor bunun yerine backendde yapılması zafiyeti ortadan kaldırır
		String userid= request.getParameter("user_id");
		Long userId = Long.parseLong(userid);
		
		Gson gson = new Gson();
        List<City> cityList = cityService.getAllCities();
        List<District> districtList = districtService.getAllDistricts();
        List<Neighborhood> neighborhoodList = neighborhoodService.getAllNeighborhood();
        List<UserProduct> userProductList = userProductService.getCurrentUsersProducts(userId);
        double totalPrice=0;
        for (UserProduct userProduct : userProductList) {
        	totalPrice = totalPrice + (userProduct.getProduct().getPrice()*userProduct.getQuantity());
		}
        model.addAttribute("userProductList", userProductList);
        model.addAttribute("cityList", cityList);
        model.addAttribute("districtListJson", gson.toJson(districtList));
        model.addAttribute("neighborhoodListJson", gson.toJson(neighborhoodList));
        model.addAttribute("totalPrice", totalPrice);
        double shipmentPrice = 0;
        if(totalPrice < 200)
        {
        	shipmentPrice=20;
        }
        model.addAttribute("shipmentPrice", shipmentPrice);
		return "checkout";
	}
	
	@Autowired
	PasswordEncoder encoder;
	@PostMapping("/resetPassword")
	public String resetPassword(HttpServletRequest request, HttpServletResponse response)
			// Telefon numarası , adres , posta kodu eklenecek
	
			throws URISyntaxException, IOException {
		String password = request.getParameter("password1");
		String email = request.getParameter("mail");

		try {
			Class.forName("org.postgresql.Driver");

			Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/hygeiaDB", "postgres",
					"admin");
			Statement stmt = con.createStatement();
			boolean hasMoreResults = stmt.execute(
					"UPDATE users SET password='"+(encoder.encode(password)+"' WHERE users.email='"+email+"';" ));

		} catch (Exception e) {
			System.out.println(e);
		}
		return "login";
	}
	@RequestMapping("/pingIP")
	public static String CommandExecution(HttpServletRequest request, HttpServletResponse response,Model model) {
		String IPaddress = request.getParameter("IPaddress");
        try {
            String comm = "cmd.exe /c ping "+IPaddress ;
            Process process = Runtime.getRuntime().exec(comm);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String commandOutput="";
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                commandOutput+=""+s+"\n";
            }
            model.addAttribute("output",commandOutput);
        } catch (IOException e) {
            System.out.println("Error executing command");
        }
       
		return "commandOutput";
    }

}
