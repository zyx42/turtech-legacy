package xyz.zyv42.turtech_legacy.store.controller;

import xyz.zyv42.turtech_legacy.store.persistence.domain.Product;
import xyz.zyv42.turtech_legacy.store.persistence.domain.User;
import xyz.zyv42.turtech_legacy.store.persistence.domain.UserReview;
import xyz.zyv42.turtech_legacy.store.persistence.service.ProductService;
import xyz.zyv42.turtech_legacy.store.persistence.service.UserReviewService;
import xyz.zyv42.turtech_legacy.store.persistence.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class ProductController {

	private final ProductService productService;
	private final UserReviewService userReviewService;
	private final UserService userService;

	@Autowired
	public ProductController(ProductService productService, UserService userService, UserReviewService userReviewService) {
		this.productService = productService;
		this.userService = userService;
		this.userReviewService = userReviewService;
	}

	@GetMapping(value = "/products")
	public String products(@PathParam("page") Integer page,
			Model model, Principal principal) {

		// handling wrong page number in pagination
		if (page == null || page <= 0) {
			page = 1;
		}

		Page<Product> productPage = productService.findAll(PageRequest.of(page - 1, 9));

		// getting page numbers
		int totalPages = productPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("activeProducts", true);
		model.addAttribute("productPage", productPage);
		model.addAttribute("activeAll", true);

		return "products";
	}

	@GetMapping(value = "/productDetails")
	public String productDetails(@PathParam("id") Long id,
			@PathParam("page") Integer page,
			Model model) {
		
		Product product = productService.findById(id);
		
		// handling wrong page number in pagination
		if (page == null || page <= 0) {
			page = 1;
		}
		
		Page<UserReview> userReviewPage = userReviewService.findByProduct(product, PageRequest.of(page - 1, 10));

		// getting page numbers
		int totalPages = userReviewPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("activeProducts", true);
		model.addAttribute("userReviewPage", userReviewPage);
		model.addAttribute("product", product);

		return "productDetails";
	}
	
	@PostMapping(value = "/leaveReview")
	public String leaveReview(@ModelAttribute("id") Long productId,
			@ModelAttribute("text") String text,
			Principal principal) {
		
		UserReview userReview = new UserReview();
		userReview.setText(text);
		Product product = productService.findById(productId);
		userReview.setProduct(product);
		User user = userService.findByUsername(principal.getName());
		userReview.setUser(user);
		userReview.setTimestamp(LocalDateTime.now());
		
		product.getUserReviewList().add(userReview);
		user.getUserReviewList().add(userReview);
		
		userReviewService.save(userReview);
		
		return "redirect:productDetails?id=" + productId + "&page=1";
	}
}
