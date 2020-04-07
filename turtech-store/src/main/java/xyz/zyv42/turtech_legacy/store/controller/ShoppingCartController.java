package xyz.zyv42.turtech_legacy.store.controller;

import xyz.zyv42.turtech_legacy.store.persistence.domain.Product;
import xyz.zyv42.turtech_legacy.store.persistence.domain.CartItem;
import xyz.zyv42.turtech_legacy.store.persistence.domain.ShoppingCart;
import xyz.zyv42.turtech_legacy.store.persistence.domain.User;
import xyz.zyv42.turtech_legacy.store.persistence.service.ProductService;
import xyz.zyv42.turtech_legacy.store.persistence.service.CartItemService;
import xyz.zyv42.turtech_legacy.store.persistence.service.ShoppingCartService;
import xyz.zyv42.turtech_legacy.store.persistence.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping(value = "/shoppingCart")
public class ShoppingCartController {

	private final UserService userService;

	private final CartItemService cartItemService;

	private final ProductService productService;

	private final ShoppingCartService shoppingCartService;

	@Autowired
	public ShoppingCartController(UserService userService, CartItemService cartItemService, ProductService productService, ShoppingCartService shoppingCartService) {
		this.userService = userService;
		this.cartItemService = cartItemService;
		this.productService = productService;
		this.shoppingCartService = shoppingCartService;
	}

	@RequestMapping(value = "/cart")
	public String shoppingCart(Model model, Principal principal) {

		User user = userService.findByUsername(principal.getName());
		ShoppingCart shoppingCart = user.getShoppingCart();

		List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);
		if (cartItemList.isEmpty()) {
			model.addAttribute("emptyCart", true);
		}
		shoppingCartService.updateShoppingCart(shoppingCart);
		int numberOfItems = 0;
		for (CartItem cartItem : cartItemList) {
			numberOfItems += cartItem.getQty();
		}

		model.addAttribute("numberOfItems", numberOfItems);
		model.addAttribute("cartItemList", cartItemList);
		model.addAttribute("shoppingCart", shoppingCart);

		return "shoppingCart";
	}

	@RequestMapping(value = "/addItem")
	public String addItem(@PathParam("id") Long id,
			@PathParam("qty") String qty,
			Model model, Principal principal) {

		User user = userService.findByUsername(principal.getName());
		Product product = productService.findById(id);
		

		if (Integer.parseInt(qty) > product.getInStockNumber()) {
			model.addAttribute("notEnoughStock", true);

			return "forward:/productDetails?id=" + product.getId();
		}

		cartItemService.addProductToCartItem(product, user, Integer.parseInt(qty));
		model.addAttribute("addProductSuccess", true);

		return "forward:/productDetails?id=" + product.getId();
	}

	@RequestMapping(value = "/updateCartItem")
	public String updateShoppingCart(@PathParam("id") Long id,
			@PathParam("qty") int qty) {

		CartItem cartItem = cartItemService.findById(id);
		cartItem.setQty(qty);
		cartItemService.updateCartItem(cartItem);

		return "forward:/shoppingCart/cart";
	}

	@RequestMapping(value = "/removeItem")
	public String removeItem(@PathParam("id") Long id) {

		cartItemService.deleteCartItem(cartItemService.findById(id));

		return "forward:/shoppingCart/cart";
	}
}
