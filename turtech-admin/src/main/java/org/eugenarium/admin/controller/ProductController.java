package org.eugenarium.admin.controller;

import org.eugenarium.admin.persistence.domain.Product;
import org.eugenarium.admin.persistence.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.websocket.server.PathParam;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public String addProduct(Model model) {

		// returning a form for adding a product
		Product product = new Product();
		model.addAttribute("product", product);

		return "addProduct";
	}

	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
	public String addProductPost(@ModelAttribute("product") Product product) {

		// adding the product to a database
		productService.save(product);

		MultipartFile productImage = product.getProductImage();

		// adding a picture of the product
		try {
			byte[] bytes = productImage.getBytes();
			String name = product.getId() + ".png";
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File("src/main/resources/static/image/product/" + name)));
			stream.write(bytes);
			stream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:productList";
	}
	
	@RequestMapping("/productInfo")
	public String productInfo(@RequestParam("id") Long id, Model model) {

		// returning a product from a database
		Product product = productService.findById(id);
		model.addAttribute("product", product);
		
		return "productInfo";
	}
	
	@RequestMapping("/updateProduct")
	public String updateProduct(@RequestParam("id") Long id, Model model) {

		// return a form for updating product
		Product product = productService.findById(id);
		model.addAttribute("product", product);
		
		return "updateProduct";
	}
	
	@RequestMapping(value = "/updateProduct", method = RequestMethod.POST)
	public String updateProductPost(@ModelAttribute("product") Product product) {

		Product currentProduct = productService.findById(product.getId());

		// updating product data
		currentProduct.setName(product.getName());
		currentProduct.setManufacturer(product.getManufacturer());
		currentProduct.setManufactureDate(product.getManufactureDate());
		currentProduct.setCategory(product.getCategory());
		currentProduct.setCondition(product.getCondition());
		currentProduct.setShippingWeight(product.getShippingWeight());
		currentProduct.setListPrice(product.getListPrice());
		currentProduct.setOurPrice(product.getOurPrice());
		currentProduct.setDescription(product.getDescription());
		currentProduct.setSpecifications(product.getSpecifications());
		currentProduct.setInStockNumber(product.getInStockNumber());
		currentProduct.setDiscontinued(product.isDiscontinued());

		// updating a product in a database
		productService.save(product);
		
		MultipartFile productImage = product.getProductImage();
		
		// updating picture of the product
		if(!productImage.isEmpty()) {
			try {
				byte[] bytes = productImage.getBytes();
				String name = product.getId() + ".png";
				
				Files.delete(Paths.get("src/main/resources/static/image/product/" + name));
				
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File("src/main/resources/static/image/product/" + name)));
				stream.write(bytes);
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return "redirect:/product/productInfo?id="+ product.getId();
	}
	
	@RequestMapping("/productList")
	public String productList(Model model) {

		// return a list of products
		List<Product> productList = productService.findAll();
		model.addAttribute("productList", productList);

		return "productList";
	}
	
	@RequestMapping(value = "/deleteProduct")
	public String deleteProduct(@PathParam("id") Long id) {

		productService.deleteById(id);
		
		return "forward:/product/productList";
	}
}