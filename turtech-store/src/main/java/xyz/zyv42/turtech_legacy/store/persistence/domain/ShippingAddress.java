package xyz.zyv42.turtech_legacy.store.persistence.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Class {@code ShippingAddress} represents an address used for shipping
 * purposes, like an actual address where the packaged order should be
 * delivered, and is part of the {@code Order} placed by a {@code User} of the
 * webstore.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Entity
@Data
@Table(name = "shipping_address", schema = "turtech")
public class ShippingAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	/**
	 * A common name for the shipping address information set, which is used
	 * to distinguish it from other sets, like when you have to choose one
	 * from a list.
	 */
	@Column(name = "shipping_address_name")
	private String shippingAddressName;

	/**
	 * A primary street part of the address information set.
	 */
	@Column(name = "shipping_address_street1")
	private String shippingAddressStreet1;

	/**
	 * A secondary street part of the address information set. It's optional.
	 */
	@Column(name = "shipping_address_street2")
	private String shippingAddressStreet2;

	/**
	 * A city part of the address information set.
	 */
	@Column(name = "shipping_address_city")
	private String shippingAddressCity;

	/**
	 * A country part of the address information set.
	 */
	@Column(name = "shipping_address_country")
	private String shippingAddressCountry;

	/**
	 * A zipcode part of the address information set.
	 */
	@Column(name = "shipping_address_zipcode")
	private String shippingAddressZipcode;

	/**
	 * An order which the billing address is part of.
	 */
	@OneToOne
	private Order order;

	/**
	 * Returns the hash code value for this ShippingAddress. It uses a prime number,
	 * <tt>id</tt>, <tt>shippingAddressName</tt> and <tt>order</tt> as means to
	 * calculate the hash code.
	 *
	 * @return the hash code value for this ShippingAddress
	 *
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	@Override
	public int hashCode() {
		return 7 * (int) id.longValue() * shippingAddressName.hashCode() * order.hashCode();
	}

	/**
	 * Indicates whether some other object is &quot;equal to&quot; this
	 * ShippingAddress. This method can return <tt>true</tt> <i>only</i> if the
	 * specified object is also a ShippingAddress and it has the same
	 * <tt>id</id> and belongs to the same <tt>order</tt>.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also a
	 *         BillingAddress and it it has the same
	 *         <tt>id</id> and belongs to the same <tt>order</tt>.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ShippingAddress))
			return false;
		ShippingAddress other = (ShippingAddress) obj;
		return id.equals(other.getId()) && shippingAddressName.equals(other.getShippingAddressName())
				&& order.equals(other.getOrder());
	}
}
