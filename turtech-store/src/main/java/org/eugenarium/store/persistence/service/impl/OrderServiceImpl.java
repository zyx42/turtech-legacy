package org.eugenarium.store.persistence.service.impl;

import org.eugenarium.store.persistence.domain.*;
import org.eugenarium.store.persistence.repository.OrderRepository;
import org.eugenarium.store.persistence.service.CartItemService;
import org.eugenarium.store.persistence.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class {@code BillingAddressService} is a general service to work with a
 * <i>BillingAddress</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	private final OrderRepository orderRepository;

	private final CartItemService cartItemService;

	@Autowired
	public OrderServiceImpl(OrderRepository orderRepository, CartItemService cartItemService) {
		this.orderRepository = orderRepository;
		this.cartItemService = cartItemService;
	}

	/**
	 * Creates a {@code Order} by using the provided information.
	 * 
	 * @param shoppingCart    - a shopping cart which contains the items to be put
	 *                        in the order.
	 * @param shippingAddress - a shipping address specified by the user.
	 * @param billingAddress  - a billing address specified by the user.
	 * @param payment         - a payment method specified by the user.
	 * @param shippingMethod  - a shipping method chosen by the user.
	 * @param user            - the user who is placing the order.
	 * @return an {@code Order} that was created.
	 */
	public synchronized Order createOrder(ShoppingCart shoppingCart, ShippingAddress shippingAddress,
			BillingAddress billingAddress, Payment payment, String shippingMethod, User user) {
		Order order = new Order();
		order.setBillingAddress(billingAddress);
		order.setOrderStatus("created");
		order.setPayment(payment);
		order.setShippingAddress(shippingAddress);
		order.setShippingMethod(shippingMethod);

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

		for (CartItem cartItem : cartItemList) {
			Product product = cartItem.getProduct();
			cartItem.setOrder(order);
			product.setInStockNumber(product.getInStockNumber() - cartItem.getQty());
		}

		order.setCartItemList(cartItemList);
		order.setOrderDate(LocalDateTime.now());
		order.setOrderTotal(shoppingCart.getGrandTotal());
		shippingAddress.setOrder(order);
		billingAddress.setOrder(order);
		payment.setOrder(order);
		order.setUser(user);
		order = orderRepository.save(order);

		return order;
	}

	/**
	 * Retrieves a {@code Order} entity by its {@code id} field.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 * @return the {@code Order} entity with the given {@code id} or
	 *         {@literal Optional#empty()} if none found
	 */
	public Order findById(Long id) {
		return orderRepository.findById(id).get();
	}
}
