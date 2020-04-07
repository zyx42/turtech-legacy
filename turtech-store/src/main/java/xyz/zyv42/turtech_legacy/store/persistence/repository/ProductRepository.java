package xyz.zyv42.turtech_legacy.store.persistence.repository;

import xyz.zyv42.turtech_legacy.store.persistence.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Class {@code ProductRepository} is an interface for generic CRUD
 * operations on a repository for {@code Product} type. Exposes all standard
 * CRUD operations due to extending Spring's {@code JpaRepository<T, ID>} and
 * provides {@code Page} return type.
 * 
 * @see JpaRepository
 * @param <Product> the domain type the repository manages
 * @param <Long>    the type of the id of the entity the repository manages
 * 
 * @author Yevhenii Zhyliaiev
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * 
	 * @param category
	 * @return
	 */
	List<Product> findByCategory(String category);

	/**
	 * Retrieves a page of {@code Product}'s by their {@code category}.
	 *
	 * @param {@code category} must not be {@literal null}.
	 * @param {@code pageable} specifies a number of items per page and a number of
	 * the page to return.
	 * @return a page of {@code Product}'s by the given {@code category}
	 */
	Page<Product> findByCategory(String category, Pageable pageable);

	/**
	 * 
	 * @param name
	 * @return
	 */
	List<Product> findByNameContainingIgnoreCase(String name);

	/**
	 * Retrieves a page of {@code Product}'s by their {@code name}.
	 *
	 * @param {@code category} must not be {@literal null}.
	 * @param {@code pageable} specifies a number of items per page and a number of
	 * the page to return.
	 * @return a page of {@code Product}'s by the given {@code name}
	 */
	Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
