package xyz.zyv42.turtech_legacy.store.persistence.service;

import xyz.zyv42.turtech_legacy.store.persistence.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Class {@code ProductService} is a general service to work with a
 * <i>Product</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface ProductService {

	/**
	 * Retrieves a list of all {@code Product}'s.
	 * 
	 * @return a list of all {@code Product}'s.
	 */
    List<Product> findAll();
    
    /**
     * Retrieves a page of {@code Product}'s.
     * 
     * @param pageRequest - contains information about the number of items per page and the number of the current page.
     * @return a page of {@code Product}'s with the given parameters of the page.
     */
    Page<Product> findAll(PageRequest pageRequest);
    
    /**
    * Retrieves a {@code Product} entity by its {@code id} field.
    * 
    * @param {@code id} must not be {@literal null}.
    * @return the {@code Product} entity with the given {@code id} or {@literal Optional#empty()} if none found
    */
    Product findById(Long id);
//    List<Product> findByCategory(String category);
    
    /**
    * Retrieves a page of {@code Product}'s by their {@code category} field.
    * 
    * @param category - a category to which the products should belong.
    * @param pageRequest - contains information about the number of items per page and the number of the current page.
    * @return the page of {@code Product}'s with the given {@code category} and parameters of the page.
    */
    Page<Product> findByCategory(String category, PageRequest pageRequest);
//    List<Product> blurrySearch(String name);
    
    /**
     * Retrieves a page of {@code Product}'s by their {@code name} field.
     * 
     * @param name - a word or other string of character that a name of the searched product should contain.
     * @param pageRequest - contains information about the number of items per page and the number of the current page.
     * @return the page of {@code Product}'s with the given {@code name} and parameters of the page.
     */
    Page<Product> blurrySearch(String name, PageRequest pageRequest);
}
