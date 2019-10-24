package org.eugenarium.store.persistence.service.impl;

import org.eugenarium.store.persistence.domain.Payment;
import org.eugenarium.store.persistence.domain.UserPayment;
import org.eugenarium.store.persistence.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class {@code PaymentService} is a general service to work with a
 * <i>Payment</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

	/**
	 * Sets a {@code Payment} by using information from {@code UserPayment}.
	 * 
	 * @param userPayment - a user payment method to get information from
	 * @param payment     - a payment method to be set
	 * @return a payment method with an information set from {@code userPayment}.
	 */
	public Payment setByUserPayment(UserPayment userPayment, Payment payment) {
		payment.setType(userPayment.getType());
		payment.setHolderName(userPayment.getHolderName());
		payment.setCardNumber(userPayment.getCardNumber());
		payment.setExpiryMonth(userPayment.getExpiryMonth());
		payment.setExpiryYear(userPayment.getExpiryYear());
		payment.setCvc(userPayment.getCvc());

		return payment;
	}
}
