package com.backend.hygeia.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.backend.hygeia.entities.Notice;
import com.backend.hygeia.entities.Product;
import com.backend.hygeia.repositories.NoticeRepository;
import com.backend.hygeia.repositories.ProductRepository;
import com.backend.hygeia.services.NoticeService;
import com.backend.hygeia.services.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestController {
	
	@Autowired
	NoticeService noticeRepository;
	
	
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }
  
	@GetMapping("/addCart")
	public void sepettenSil() {
;
		Notice notice = new Notice(5L,"B","B",1,"B","B","B","B");
		Notice noticea = noticeRepository.save(notice);

	}
}
