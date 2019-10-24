package org.eugenarium.store.persistence.repository;

import org.eugenarium.store.persistence.domain.CartItem;
import org.eugenarium.store.persistence.domain.Order;
import org.eugenarium.store.persistence.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Class {@code CartItemRepository} is an interface for generic CRUD
 * operations on a repository for {@code CartItem} type. Exposes all standard
 * CRUD operations due to extending Spring's {@code CrudRepository<T, ID>}.
 * 
 * @see CrudRepository
 * @param <CartItem> the domain type the repository manages
 * @param <Long>     the type of the id of the entity the repository manages
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface CartItemRepository extends CrudRepository<CartItem, Long> {

	/**
	 * Retrieves a {@code CartItem} entity by its {@code shoppingCart} field.
	 *
	 * @param {@code shoppingCart} must not be {@literal null}.
	 * @return the {@code CartItem} entity with the given {@code shoppingCart} or {@literal Optional#empty()} if none found
	 */
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

	/**
	 * Retrieves a {@code CartItem} entity by its {@code order} field.
	 *
	 * @param order must not be {@literal null}.
	 * @return the {@code CartItem} entity with the given {@code order} or {@literal Optional#empty()} if none found
	 */
	List<CartItem> findByOrder(Order order);
}