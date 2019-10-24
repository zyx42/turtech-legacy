package org.eugenarium.store.persistence.service.impl;

import org.eugenarium.store.persistence.domain.Product;
import org.eugenarium.store.persistence.repository.ProductRepository;
import org.eugenarium.store.persistence.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code ProductService} is a general service to work with a
 * <i>Product</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;

	@Autowired
	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	/**
	 * Retrieves a list of all {@code Product}'s.
	 * 
	 * @return a list of all {@code Product}'s.
	 */
	public List<Product> findAll() {
		List<Product> productList = productRepository.findAll();
		List<Product> activeProductList = new ArrayList<>();

		for (Product product : productList) {
			if (!product.isDiscontinued()) {
				activeProductList.add(product);
			}
		}

		return activeProductList;
	}

	/**
	 * Retrieves a page of {@code Product}'s.
	 * 
	 * @param pageRequest - contains information about the number of items per page
	 *                    and the number of the current page.
	 * @return a page of {@code Product}'s with the given parameters of the page.
	 */
	public Page<Product> findAll(PageRequest pageRequest) {
		return productRepository.findAll(pageRequest);
	}

	/**
	 * Retrieves a {@code Product} entity by its {@code id} field.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 * @return the {@code Product} entity with the given {@code id} or
	 *         {@literal Optional#empty()} if none found
	 */
	public Product findById(Long id) {
		return productRepository.findById(id).get();
	}

	/**
	 * Retrieves a page of {@code Product}'s by their {@code category} field.
	 * 
	 * @param category    - a category to which the products should belong.
	 * @param pageRequest - contains information about the number of items per page
	 *                    and the number of the current page.
	 * @return the page of {@code Product}'s with the given {@code category} and
	 *         parameters of the page.
	 */
	public Page<Product> findByCategory(String category, PageRequest pageRequest) {

		return productRepository.findByCategory(category, pageRequest);
	}

	/**
	 * Retrieves a page of {@code Product}'s by their {@code name} field.
	 * 
	 * @param name        - a word or other string of character that a name of the
	 *                    searched product should contain.
	 * @param pageRequest - contains information about the number of items per page
	 *                    and the number of the current page.
	 * @return the page of {@code Product}'s with the given {@code name} and
	 *         parameters of the page.
	 */
	public Page<Product> blurrySearch(String name, PageRequest pageRequest) {

		return productRepository.findByNameContainingIgnoreCase(name, pageRequest);
	}
}
