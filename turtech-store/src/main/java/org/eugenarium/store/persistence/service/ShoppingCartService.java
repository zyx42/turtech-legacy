package org.eugenarium.store.persistence.service;

import org.eugenarium.store.persistence.domain.ShoppingCart;

/**
 * Class {@code ShoppingCartService} is a general service to work with a
 * <i>ShoppingCart</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface ShoppingCartService {

	/**
	 * Adds a {@code ShoppingCart} entity to a persistent context, which means that
	 * any further changes to it will be tracked and saved when transaction is
	 * committed.
	 * 
	 * @param shoppingCart - a shopping cart to be added to a persistent context.
	 * @return the shopping cart which was added to a persistent context.
	 */
	ShoppingCart updateShoppingCart(ShoppingCart shoppingCart);

	/**
	 * Empties a {@code ShoppingCart} from items in it.
	 * 
	 * @param shoppingCart - a shopping cart to be emptied from items.
	 */
	void clearShoppingCart(ShoppingCart shoppingCart);

	/**
	 * Saves a {@code ShoppingCart} entity to a database.
	 * 
	 * @param shoppingCart - a shopping cart to be saved
	 * @return the shopping cart that has been saved.
	 */
	void saveShoppingCart(ShoppingCart shoppingCart);
}
