package com.backend.hygeia.resources;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

import java.io.Console;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

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
	
	private static final Logger logger = LogManager.getLogger("HomePageController");
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/")
	String getProducts(Model model) {
		logger.trace("Entering method processOrder().");
		logger.debug("Received order with ID 12345.");
		logger.info("Order shipped successfully.");
		logger.warn("Potential security vulnerability detected in user input: '...'");
		logger.error("Failed to process order. Error: {. . .}");
		logger.fatal("System crashed. Shutting down...");
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
        //Return edilen isim sayfanın ismidir index.html ye götürür buraya gelen istekleri
        return "index";
	}
	@GetMapping("/login")
	String login() {
        return "login";
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
	


}
