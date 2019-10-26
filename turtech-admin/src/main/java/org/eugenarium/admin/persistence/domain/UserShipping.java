package org.eugenarium.admin.persistence.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Class {@code UserShipping} represents shipping information which is added to
 * a particular customer account.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Entity
@Data
@Table(name = "user_shipping", schema = "turtech")
public class UserShipping implements Serializable {

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
	@Column(name = "user_shipping_name")
	private String userShippingName;

	/**
	 * A primary street part of the address information set.
	 */
	@Column(name = "user_shipping_street1")
	private String userShippingStreet1;

	/**
	 * A secondary street part of the address information set. It's optional.
	 */
	@Column(name = "user_shipping_street2")
	private String userShippingStreet2;

	/**
	 * A city part of the address information set.
	 */
	@Column(name = "user_shipping_city")
	private String userShippingCity;

	/**
	 * A country part of the address information set.
	 */
	@Column(name = "user_shipping_country")
	private String userShippingCountry;

	/**
	 * A zipcode part of the address information set.
	 */
	@Column(name = "user_shipping_zipcode")
	private String userShippingZipcode;

	/**
	 * Specifies if the user chose the shipping address as his default shipping
	 * address or not.
	 */
	@Column(name = "user_shipping_default")
	private boolean userShippingDefault;

	/**
	 * A user to which the shipping address is assigned.
	 */
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	/**
	 * Returns the hash code value for this UserShipping. It uses a prime number,
	 * <tt>id</tt> and <tt>userShippingName</tt> as means to calculate the hash
	 * code.
	 *
	 * @return the hash code value for this UserShipping
	 *
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	@Override
	public int hashCode() {
		return 7 * (int) id.longValue() * userShippingName.hashCode();
	}

	/**
	 * Indicates whether some other object is &quot;equal to&quot; this
	 * UserShipping. This method can return <tt>true</tt> <i>only</i> if the
	 * specified object is also a UserPayment and it has the same
	 * <tt>id</id> and <tt>userShippingName</tt>.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also a UserShipping
	 *         and it it has the same <tt>id</id> and <tt>userShippingName</tt>.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserShipping))
			return false;
		UserShipping other = (UserShipping) obj;
		return id.equals(other.getId()) && userShippingName.equals(other.getUserShippingName());
	}
}
