package org.eugenarium.store.persistence.repository;

import org.eugenarium.store.persistence.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

/**
 * Class {@code ShoppingCartRepository} is an interface for generic CRUD
 * operations on a repository for {@code ShoppingCart} type. Exposes all
 * standard CRUD operations due to extending Spring's
 * {@code CrudRepository<T, ID>}.
 * 
 * @see CrudRepository
 * @param <ShoppingCart> the domain type the repository manages
 * @param <Long>         the type of the id of the entity the repository manages
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface ShoppingCartRepository extends CrudRepository<ShoppingCart, Long> {
}
