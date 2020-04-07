package xyz.zyv42.turtech_legacy.store.persistence.service;

import org.eugenarium.store.persistence.domain.*;
import xyz.zyv42.store.persistence.domain.*;
import xyz.zyv42.turtech_legacy.store.persistence.domain.*;

import java.util.List;

/**
 * Class {@code CartItemService} is a general service to work with a
 * <i>CartItem</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface CartItemService {

	/**
	 * Retrieves a list of {@code CartItem}'s by their {@code shoppingCart} field.
	 * 
	 * @param shoppingCart - a shopping cart which contains the cart items
	 * @return a list of {@code CartItem}'s with the given {@code shoppingCart}
	 */
	List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

	/**
	 * Adds a {@code CartItem} entity to a persistent context, which means that any
	 * further changes to it will be tracked and saved when transaction is
	 * committed.
	 * 
	 * @param cartItem - a cart item to be added to a persistent context.
	 * @return the cart item which was added to a persistent context.
	 */
	CartItem updateCartItem(CartItem cartItem);

	/**
	 * Wraps a {@code Product} into a {@code ProductToCartItem}.
	 * 
	 * @param product - a product to be wrapped into a {@code ProductToCartItem}.
	 * @param user    - a user who added a product into his shopping cart.
	 * @param qty     - a quantity of the added product.
	 * @return the cart item which represents a product in a shopping cart.
	 */
	CartItem addProductToCartItem(Product product, User user, int qty);

	/**
	 * Retrieves a {@code CartItem} entity by its {@code id} field.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 * @return the {@code CartItem} entity with the given {@code id} or
	 *         {@literal Optional#empty()} if none found
	 */
	CartItem findById(Long id);

	/**
	 * Deletes a {@code CartItem} entity.
	 * 
	 * @param cartItem - a cart item to be deleted.
	 */
	void deleteCartItem(CartItem cartItem);

	/**
	 * Saves a {@code CartItem} entity to a database.
	 * 
	 * @param cartItem - a cart item to be saved
	 * @return the cart item that was saved.
	 */
	CartItem save(CartItem cartItem);

	/**
	 * Retrieves a list of {@code CartItem}'s by their {@code order} field.
	 * 
	 * @param order - an order which contains the cart items
	 * @return a list of {@code CartItem}'s with the given {@code order}
	 */
	List<CartItem> findByOrder(Order order);
}
