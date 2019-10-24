package org.eugenarium.admin.persistence.service.impl;

import org.eugenarium.admin.persistence.domain.Product;
import org.eugenarium.admin.persistence.repository.ProductRepository;
import org.eugenarium.admin.persistence.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	 * Saves a {@code Product} entity to a database.
	 * 
	 * @param product - a product to be saved
	 * @return the product that was saved.
	 */
	public Product save(Product product) {
		return productRepository.save(product);
	}

	/**
	 * Retrieves a list of all {@code Product}'s.
	 * 
	 * @return a list of all {@code Product}'s.
	 */
	public List<Product> findAll() {
		return (List<Product>) productRepository.findAll();
	}

	/**
    * Retrieves a {@code Product} entity by its {@code id} field.
    * 
    * @param {@code id} must not be {@literal null}.
    * @return the {@code Product} entity with the given {@code id} or {@literal Optional#empty()} if none found
    */
	public Product findById(Long id) {
		return productRepository.findById(id).get();
	}

	/**
    * Find a {@code Product} entity by its {@code id} field and deletes it from the database.
    * 
    * @param {@code id} must not be {@literal null}.
    */
	public void deleteById(Long id) {
		productRepository.deleteById(id);
	}
}
