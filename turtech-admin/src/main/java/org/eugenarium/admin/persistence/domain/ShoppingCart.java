package org.eugenarium.admin.persistence.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Class {@code ShoppingCart} represents a shopping cart used by a {@code User} to
 * store {@code CartItem} before actually purchasing them.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Entity
@Data
@Table(name = "shopping_cart", schema = "turtech")
public class ShoppingCart implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	/**
	 * A joint sum of the cart items' prices with no tax applied.
	 */
	@Column(name = "grand_total")
	private BigDecimal grandTotal;

	/**
	 * A list of items that the user has put into the shopping cart.
	 */
	@OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<CartItem> cartItemList;

	/**
	 * The user to which the shopping cart belongs.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private User user;

	/**
	 * Returns the hash code value for this ShoppingCart. It uses a prime number,
	 * <tt>id</tt> and <tt>user</tt> as means to calculate the hash code.
	 *
	 * @return the hash code value for this ShoppingCart
	 *
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	@Override
	public int hashCode() {
		return 7 * (int) id.longValue() * user.hashCode();
	}

	/**
	 * Indicates whether some other object is &quot;equal to&quot; this ShoppingCart.
	 * This method can return <tt>true</tt> <i>only</i> if the specified object is
	 * also a ShoppingCart and it has the same
	 * <tt>id</id> and belongs to the same <tt>user</tt>.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also a ShoppingCart
	 *         and it it has the same
	 *         <tt>id</id> and belongs to the same <tt>user</tt>.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ShoppingCart))
			return false;
		ShoppingCart other = (ShoppingCart) obj;
		return id.equals(other.getId()) && user.equals(other.getUser());
	}
}
