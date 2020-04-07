package xyz.zyv42.turtech_legacy.admin.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Class {@code CartItem} represents a {@code Product} from the store which is
 * currently in a {@code ShoppingCart}.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Entity
@Data
@Table(name = "cart_item", schema = "turtech")
public class CartItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	/**
	 * The quantity of this particular item type in a shopping cart.
	 */
	@Column(name = "qty")
	private int qty;

	/**
	 * The subtotal which implies the joint price of all items in a shopping cart
	 * with no tax applied.
	 */
	@Column(name = "subtotal")
	private BigDecimal subtotal;

	/**
	 * It's here because of the fact that even though one cart item is definitely
	 * one particular product, many users can have the same product in their
	 * shopping carts, leading to many cart items being the same product.
	 */
	@ManyToOne
	@JoinColumn(name = "product_id")
	@JsonIgnore
	private Product product;

	/**
	 * A shopping cart to which the cart item belongs.
	 */
	@ManyToOne
	@JoinColumn(name = "shopping_cart_id")
	private ShoppingCart shoppingCart;

	/**
	 * An order which the cart item is part of.
	 */
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	/**
	 * Returns the hash code value for this CartItem. It uses a prime number,
	 * <tt>id</tt> and <tt>shoppingCart</tt> as means to calculate the hash code.
	 *
	 * @return the hash code value for this CartItem
	 *
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	@Override
	public int hashCode() {
		return 7 * (int) id.longValue() * shoppingCart.hashCode();
	}

	/**
	 * Indicates whether some other object is &quot;equal to&quot; this CartItem.
	 * This method can return <tt>true</tt> <i>only</i> if the specified object is
	 * also a CartItem and it has the same <tt>id</id> and <tt>shoppingCart</tt>.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also a CartItem.
	 *         and it it has the same <tt>id</id> and <tt>shoppingCart</tt>.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CartItem))
			return false;
		CartItem other = (CartItem) obj;
		return id.equals(other.getId()) && shoppingCart.equals(other.getShoppingCart());
	}

	/**
	 * Returns a string which represents a {@code CartItem} object. I uses <tt>id</tt>,
	 * <tt>qty</tt>, <tt>subtotal</tt> and <tt>product</tt> fields to build the String.
	 * 
	 * @return a String representation of a particular CartItem object.
	 * 
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return "CartItem{id=" + id + ",qty=" + qty + ",subtotal=" + subtotal + ",product=" + product.getName()
				+ ",shoppingCart=" + shoppingCart.getId() + "}";
	}
}
