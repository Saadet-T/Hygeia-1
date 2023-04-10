package com.backend.hygeia.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

import com.backend.hygeia.entities.Category;
import com.backend.hygeia.entities.Notice;
import com.backend.hygeia.entities.Product;
import com.backend.hygeia.entities.UserProduct;
import com.backend.hygeia.repositories.ProductRepository;
import com.backend.hygeia.services.CategoryService;
import com.backend.hygeia.services.NoticeService;
import com.backend.hygeia.services.ProductService;
import com.backend.hygeia.services.UserProductService;
import com.backend.hygeia.utils.UserProductMapper;
import com.google.gson.Gson;

import java.io.Console;
import java.util.*;

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
	
	@GetMapping("/")
	String getProducts(Model model) {
		Gson gson = new Gson();
        Map<Product,String> productList = productService.getAllProductsWithJson();
        List<Category> categoryList = categoryService.getAllCategories();
        List<Notice> noticeList = noticeService.getAllNotices();
        List<UserProduct> userProductList = userProductService.getAllUserProducts();
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
	@GetMapping("/updateUserInfo")
	String updateUserInfo() {
		return "updateUserInfo";
	}
	


}
