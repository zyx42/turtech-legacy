package org.eugenarium.store.controller;

import org.eugenarium.store.persistence.domain.Product;
import org.eugenarium.store.persistence.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class SearchController {

	private final ProductService productService;

	@Autowired
	public SearchController(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping("/searchByCategory")
	public String searchByCategory(@RequestParam("category") String category,
			@PathParam("pageNumber") Integer pageNumber,
			Model model, Principal principal) {

		// handling wrong page number in pagination
		if (pageNumber == null || pageNumber <= 0) {
			pageNumber = 1;
		}

		String classActiveCategory = "active" + category;
		classActiveCategory = classActiveCategory.replaceAll("\\s+", "");
		classActiveCategory = classActiveCategory.replaceAll("&", "");
		model.addAttribute(classActiveCategory, true);

		Page<Product> productPage = productService.findByCategory(category, PageRequest.of(pageNumber - 1, 9));

		// getting page number
		int totalPages = productPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("activeProducts", true);
		model.addAttribute("productPage", productPage);

		return "products";
	}

	@RequestMapping("/searchProduct")
	public String searchProduct(@ModelAttribute("keyword") String keyword,
			@PathParam("pageNumber") Integer pageNumber,
			Principal principal, Model model) {

		// handling wrong page number in pagination
		if (pageNumber == null || pageNumber <= 0) {
			pageNumber = 1;
		}

		Page<Product> productPage = productService.blurrySearch(keyword, PageRequest.of(pageNumber - 1, 9));

		// getting page number
		int totalPages = productPage.getTotalPages();
		if (totalPages > 0) {
			List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
					.boxed()
					.collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}

		model.addAttribute("activeProducts", true);
		model.addAttribute("productPage", productPage);

		return "products";
	}
}
