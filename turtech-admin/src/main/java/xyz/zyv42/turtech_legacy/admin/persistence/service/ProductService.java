package xyz.zyv42.turtech_legacy.admin.persistence.service;

import xyz.zyv42.turtech_legacy.admin.persistence.domain.Product;

import java.util.List;

/**
 * Class {@code ProductService} is a general service to work with a
 * <i>Product</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface ProductService {
	
	/**
	 * Saves a {@code Product} entity to a database.
	 * 
	 * @param product - a product to be saved
	 * @return the product that was saved.
	 */
	Product save(Product product);

	/**
	 * Retrieves a list of all {@code Product}'s.
	 * 
	 * @return a list of all {@code Product}'s.
	 */
	List<Product> findAll();

	/**
    * Retrieves a {@code Product} entity by its {@code id} field.
    * 
    * @param {@code id} must not be {@literal null}.
    * @return the {@code Product} entity with the given {@code id} or {@literal Optional#empty()} if none found
    */
	Product findById(Long id);

	/**
    * Find a {@code Product} entity by its {@code id} field and deletes it from the database.
    * 
    * @param {@code id} must not be {@literal null}.
    */
	void deleteById(Long id);
}
