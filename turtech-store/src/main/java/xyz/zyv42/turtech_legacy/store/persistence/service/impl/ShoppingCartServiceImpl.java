package xyz.zyv42.turtech_legacy.store.persistence.service.impl;

import xyz.zyv42.turtech_legacy.store.persistence.domain.CartItem;
import xyz.zyv42.turtech_legacy.store.persistence.domain.ShoppingCart;
import xyz.zyv42.turtech_legacy.store.persistence.repository.ShoppingCartRepository;
import xyz.zyv42.turtech_legacy.store.persistence.service.CartItemService;
import xyz.zyv42.turtech_legacy.store.persistence.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Class {@code ShoppingCartService} is a general service to work with a
 * <i>ShoppingCart</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private final CartItemService cartItemService;

	private final ShoppingCartRepository shoppingCartRepository;

	@Autowired
	public ShoppingCartServiceImpl(CartItemService cartItemService, ShoppingCartRepository shoppingCartRepository) {
		this.cartItemService = cartItemService;
		this.shoppingCartRepository = shoppingCartRepository;
	}

	/**
	 * Adds a {@code ShoppingCart} entity to a persistent context, which means that
	 * any further changes to it will be tracked and saved when transaction is
	 * committed.
	 * 
	 * @param shoppingCart - a shopping cart to be added to a persistent context.
	 * @return the shopping cart which was added to a persistent context.
	 */
	public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {
		BigDecimal cartTotal = new BigDecimal(0);

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

		for (CartItem cartItem : cartItemList) {
			if (cartItem.getProduct().getInStockNumber() > 0) {
				cartItemService.updateCartItem(cartItem);
				cartTotal = cartTotal.add(cartItem.getSubtotal());
			}
		}

		shoppingCart.setGrandTotal(cartTotal);

		shoppingCartRepository.save(shoppingCart);

		return shoppingCart;
	}

	/**
	 * Empties a {@code ShoppingCart} from items in it.
	 * 
	 * @param shoppingCart - a shopping cart to be emptied from items.
	 */
	public void clearShoppingCart(ShoppingCart shoppingCart) {
		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

		for (CartItem cartItem : cartItemList) {
			cartItem.setShoppingCart(null);
			cartItemService.save(cartItem);
		}

		shoppingCart.setGrandTotal(new BigDecimal(0));

		shoppingCartRepository.save(shoppingCart);
	}

	/**
	 * Saves a {@code ShoppingCart} entity to a database.
	 * 
	 * @param shoppingCart - a shopping cart to be saved
	 * @return the shopping cart that has been saved.
	 */
	public void saveShoppingCart(ShoppingCart shoppingCart) {
		shoppingCartRepository.save(shoppingCart);
	}
}
