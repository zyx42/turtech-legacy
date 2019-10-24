package org.eugenarium.store.persistence.service.impl;

import org.eugenarium.store.persistence.domain.*;
import org.eugenarium.store.persistence.repository.CartItemRepository;
import org.eugenarium.store.persistence.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Class {@code CartItemService} is a general service to work with a
 * <i>CartItem</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Service
@Transactional
public class CartItemServiceImpl implements CartItemService {

	private final CartItemRepository cartItemRepository;

	@Autowired
	public CartItemServiceImpl(CartItemRepository cartItemRepository) {
		this.cartItemRepository = cartItemRepository;
	}

	/**
	 * Retrieves a list of {@code CartItem}'s by their {@code shoppingCart} field.
	 * 
	 * @param shoppingCart - a shopping cart which contains the cart items
	 * @return a list of {@code CartItem}'s with the given {@code shoppingCart}
	 */
	public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
		return cartItemRepository.findByShoppingCart(shoppingCart);
	}

	/**
	 * Adds a {@code CartItem} entity to a persistent context, which means that any
	 * further changes to it will be tracked and saved when transaction is
	 * committed.
	 * 
	 * @param cartItem - a cart item to be added to a persistent context.
	 * @return the cart item which was added to a persistent context.
	 */
	public CartItem updateCartItem(CartItem cartItem) {
		BigDecimal bigDecimal = BigDecimal.valueOf(cartItem.getProduct().getOurPrice())
				.multiply(new BigDecimal(cartItem.getQty()));

		bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
		cartItem.setSubtotal(bigDecimal);

		cartItemRepository.save(cartItem);

		return cartItem;
	}

	/**
	 * Wraps a {@code Product} into a {@code ProductToCartItem}.
	 * 
	 * @param product - a product to be wrapped into a {@code ProductToCartItem}.
	 * @param user    - a user who added a product into his shopping cart.
	 * @param qty     - a quantity of the added product.
	 * @return the cart item which represents a product in a shopping cart.
	 */
	public CartItem addProductToCartItem(Product product, User user, int qty) {
		List<CartItem> cartItemList = findByShoppingCart(user.getShoppingCart());

		for (CartItem cartItem : cartItemList) {
			if (product.getId().equals(cartItem.getProduct().getId())) {
				cartItem.setQty(cartItem.getQty() + qty);
				cartItem.setSubtotal(BigDecimal.valueOf(product.getOurPrice()).multiply(new BigDecimal(qty)));
				cartItemRepository.save(cartItem);
				return cartItem;
			}
		}

		CartItem cartItem = new CartItem();
		cartItem.setShoppingCart(user.getShoppingCart());
		cartItem.setProduct(product);

		cartItem.setQty(qty);
		cartItem.setSubtotal(BigDecimal.valueOf(product.getOurPrice()).multiply(new BigDecimal(qty)));
		cartItem = cartItemRepository.save(cartItem);

		return cartItem;
	}

	/**
	 * Retrieves a {@code CartItem} entity by its {@code id} field.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 * @return the {@code CartItem} entity with the given {@code id} or
	 *         {@literal Optional#empty()} if none found
	 */
	public CartItem findById(Long id) {
		return cartItemRepository.findById(id).get();
	}

	/**
	 * Deletes a {@code CartItem} entity.
	 * 
	 * @param cartItem - a cart item to be deleted.
	 */
	public void deleteCartItem(CartItem cartItem) {
		cartItemRepository.delete(cartItem);
	}

	/**
	 * Saves a {@code CartItem} entity to a database.
	 * 
	 * @param cartItem - a cart item to be saved
	 * @return the cart item that was saved.
	 */
	public CartItem save(CartItem cartItem) {
		return cartItemRepository.save(cartItem);
	}

	/**
	 * Retrieves a list of {@code CartItem}'s by their {@code order} field.
	 * 
	 * @param order - an order which contains the cart items
	 * @return a list of {@code CartItem}'s with the given {@code order}
	 */
	public List<CartItem> findByOrder(Order order) {
		return cartItemRepository.findByOrder(order);
	}
}
