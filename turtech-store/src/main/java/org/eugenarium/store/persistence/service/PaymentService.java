package org.eugenarium.store.persistence.service;

import org.eugenarium.store.persistence.domain.UserPayment;
import org.eugenarium.store.persistence.domain.Payment;

/**
 * Class {@code PaymentService} is a general service to work with a
 * <i>Payment</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface PaymentService {

	/**
	 * Sets a {@code Payment} by using information from {@code UserPayment}.
	 * 
	 * @param userPayment - a user payment method to get information from
	 * @param payment     - a payment method to be set
	 * @return a payment method with an information set from {@code userPayment}.
	 */
	Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
