package xyz.zyv42.turtech_legacy.store.persistence.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Class {@code BillingAddress} represents an address which is used for billing
 * purposes, like where the bill should be "sent", when constructing an
 * {@code Order}. Usually is the same as the shipping address.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Entity
@Data
@Table(name = "billing_address", schema = "turtech")
public class BillingAddress implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	/**
	 * A common name for the billing address information set, which is used
	 * to distinguish it from other sets, like when you have to choose one
	 * from a list.
	 */
	@Column(name = "billing_address_name")
	private String billingAddressName;

	/**
	 * A primary street part of the address information set.
	 */
	@Column(name = "billing_address_street1")
	private String billingAddressStreet1;

	/**
	 * A secondary street part of the address information set. It's optional.
	 */
	@Column(name = "billing_address_street2")
	private String billingAddressStreet2;

	/**
	 * A city part of the address information set.
	 */
	@Column(name = "billing_address_city")
	private String billingAddressCity;

	/**
	 * A country part of the address information set.
	 */
	@Column(name = "billing_address_country")
	private String billingAddressCountry;

	/**
	 * A zipcode part of the address information set.
	 */
	@Column(name = "billing_address_zipcode")
	private String billingAddressZipcode;

	/**
	 * An order which the billing address is part of.
	 */
	@OneToOne
	private Order order;

	/**
	 * Returns the hash code value for this BillingAddress. It uses a prime number,
	 * <tt>id</tt> and <tt>order</tt> as means to calculate the hash code.
	 *
	 * @return the hash code value for this BillingAddress
	 *
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	@Override
	public int hashCode() {
		return 7 * (int) id.longValue() * order.hashCode();
	}

	/**
	 * Indicates whether some other object is &quot;equal to&quot; this
	 * BillingAddress. This method can return <tt>true</tt> <i>only</i> if the
	 * specified object is also a User and it has the same
	 * <tt>id</id> and <tt>order</tt>.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also a
	 *         BillingAddress. and it it has the same
	 *         <tt>id</id> and <tt>order</tt>.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof BillingAddress))
			return false;
		BillingAddress other = (BillingAddress) obj;
		return id.equals(other.getId()) && order.equals(other.getOrder());
	}
}
