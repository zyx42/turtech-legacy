package org.eugenarium.store.persistence.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.*;

/**
 * Class {@code Payment} represents payment information which a {@code User}
 * provides to the webstore to place an {@code Order}. Currently it encompasses only
 * credit cards.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Entity
@Data
@Table(name = "payment", schema = "turtech")
public class Payment implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	/**
	 * Specifies a company by which the credit card was issued, VISA or Mastercard.
	 */
	@Column(name = "type")
	private String type;

	/**
	 * A common name for the whole information set about the credit card, used
	 * to distinguish one set from another, like when you have to choose
	 * a payment method from a list.
	 */
	@Column(name = "card_name")
	private String cardName;

	/**
	 * A credit card number which is assigned by the bank that issued it.
	 */
	@Column(name = "card_number")
	private String cardNumber;

	/**
	 * A month part of the date on which the credit card expires.
	 */
	@Column(name = "expiry_month")
	private int expiryMonth;

	/**
	 * A year part of the date on which the credit card expires.
	 */
	@Column(name = "expiry_year")
	private int expiryYear;

	/**
	 * Card Verification Code, usually consists of three numbers located to the right
	 * from the credit card number.
	 */
	@Column(name = "cvc")
	private int cvc;

	/**
	 * A name of the credit card holder, as it was registered by the bank that
	 * issued the card.
	 */
	@Column(name = "holder_name")
	private String holderName;

	/**
	 * An order that was placed with the payment method specified.
	 */
	@OneToOne
	private Order order;

	/**
	 * A user billing is a set of payment information which is saved by a
	 * particular user on his/her account in order to be used later.
	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "userPayment")
	private UserBilling userBilling;

	/**
	 * Returns the hash code value for this Payment. It uses a prime number,
	 * <tt>id</tt> and <tt>order</tt> as means to calculate the hash code.
	 *
	 * @return the hash code value for this Payment
	 *
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	@Override
	public int hashCode() {
		return 7 * (int) id.longValue() * order.hashCode();
	}

	/**
	 * Indicates whether some other object is &quot;equal to&quot; this Payment.
	 * This method can return <tt>true</tt> <i>only</i> if the specified object is
	 * also a Payment and it has the same <tt>id</id> and <tt>order</tt>.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also an Payment.
	 *         and it it has the same <tt>id</id> and <tt>order</tt>.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Payment))
			return false;
		Payment other = (Payment) obj;
		return id.equals(other.getId()) && order.equals(other.getOrder());
	}
}
