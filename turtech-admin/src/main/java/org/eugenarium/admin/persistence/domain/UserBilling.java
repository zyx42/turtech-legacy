package org.eugenarium.admin.persistence.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Class {@code UserBilling} represents billing information which was saved to a
 * particular user's account for later usage.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Entity
@Data
@Table(name = "user_billing", schema = "turtech")
public class UserBilling implements Serializable {

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
	@Column(name = "user_billing_name")
	private String userBillingName;

	/**
	 * A primary street part of the address information set.
	 */
	@Column(name = "user_billing_street1")
	private String userBillingStreet1;

	/**
	 * A secondary street part of the address information set. It's optional.
	 */
	@Column(name = "user_billing_street2")
	private String userBillingStreet2;

	/**
	 * A city part of the address information set.
	 */
	@Column(name = "user_billing_city")
	private String userBillingCity;

	/**
	 * A country part of the address information set.
	 */
	@Column(name = "user_billing_country")
	private String userBillingCountry;

	/**
	 * A zipcode part of the address information set.
	 */
	@Column(name = "user_billing_zipcode")
	private String userBillingZipcode;

	/**
	 * A user's payment method(credit card) with which the billing address
	 * information set was specified.
	 */
	@OneToOne(cascade = CascadeType.ALL)
	private UserPayment userPayment;

	/**
	 * Returns the hash code value for this UserBilling. It uses a prime number,
	 * <tt>id</tt> and <tt>userBllingName</tt> as means to calculate the hash code.
	 *
	 * @return the hash code value for this UserBilling
	 *
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	@Override
	public int hashCode() {
		return 7 * (int) id.longValue() * userBillingName.hashCode();
	}

	/**
	 * Indicates whether some other object is &quot;equal to&quot; this UserBilling.
	 * This method can return <tt>true</tt> <i>only</i> if the specified object is
	 * also a UserBilling and it has the same
	 * <tt>id</id> and <tt>userBillingName</tt>.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also a UserBilling
	 *         and it it has the same <tt>id</id> and <tt>userBillingName</tt>.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserBilling))
			return false;
		UserBilling other = (UserBilling) obj;
		return id.equals(other.getId()) && userBillingName.equals(other.getUserBillingName());
	}
}
