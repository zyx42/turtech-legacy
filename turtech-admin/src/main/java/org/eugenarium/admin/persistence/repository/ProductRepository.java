package org.eugenarium.admin.persistence.repository;

import org.eugenarium.admin.persistence.domain.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Class {@code ProductRepository} is an interface for generic CRUD
 * operations on a repository for {@code Product} type. Exposes all standard
 * CRUD operations due to extending Spring's {@code CrudRepository<T, ID>}.
 * 
 * @see CrudRepository
 * @param <Product> the domain type the repository manages
 * @param <Long>    the type of the id of the entity the repository manages
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface ProductRepository extends CrudRepository<Product, Long> {
}