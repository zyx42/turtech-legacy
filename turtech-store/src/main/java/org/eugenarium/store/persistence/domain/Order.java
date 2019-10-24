package org.eugenarium.store.persistence.domain;

import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Class {@code Order} represents an order which a {@code User} has placed.
 * It contains a {@code ShippingAddress}, {@code BillingAddress}, {@code Payment}
 * and a list of {@code CartItem}'s.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Entity
@Data
@Table(name = "user_order", schema = "turtech")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * A date at which the order has been placed.
	 */
	@Column(name = "order_date")
	private LocalDateTime orderDate;

	/**
	 * An approximate date at which the order should be delivered.
	 */
	@Column(name = "shipping_date")
	private LocalDateTime shippingDate;

	/**
	 * Describes a shipping method for the order, is it premium or not.
	 */
	@Column(name = "shipping_method")
	private String shippingMethod;

	/**
	 * A status of the order, or rather at which stage of shipping it is.
	 */
	@Column(name = "order_status")
	private String orderStatus;

	/**
	 * The order's total, which is the joint price of all the items in the order
	 * and tax applied.
	 */
	@Column(name = "order_total")
	private BigDecimal orderTotal;

	/**
	 * The list of items that constitutes the order itself.
	 */
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<CartItem> cartItemList;

	/**
	 * The address to which the actual packaged order is sent.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private ShippingAddress shippingAddress;

	/**
	 * The address to which the bill is "sent". Usually the same as 
	 * shipping address.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private BillingAddress billingAddress;

	/**
	 * General information about the credit card that was used by the
	 * customer to place the order.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private Payment payment;

	/**
	 * The user that has placed the order.
	 */
	@ManyToOne
	private User user;

	/**
	 * Returns the hash code value for this Order. It uses a prime number,
	 * <tt>id</tt> and <tt>user</tt> as means to calculate the hash code.
	 *
	 * @return the hash code value for this Order
	 *
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	@Override
	public int hashCode() {
		return 7 * (int) id.longValue() * user.hashCode();
	}

	/**
	 * Indicates whether some other object is &quot;equal to&quot; this Order. This
	 * method can return <tt>true</tt> <i>only</i> if the specified object is also
	 * an Order and it has the same <tt>id</id> and <tt>user</tt>.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also an Order. and
	 *         it it has the same <tt>id</id> and <tt>user</tt>.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Order))
			return false;
		Order other = (Order) obj;
		return id.equals(other.getId()) && user.equals(other.getUser());
	}
}
