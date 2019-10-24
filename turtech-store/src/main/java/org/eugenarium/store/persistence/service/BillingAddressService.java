package org.eugenarium.store.persistence.service;

import org.eugenarium.store.persistence.domain.UserBilling;
import org.eugenarium.store.persistence.domain.BillingAddress;

/**
 * Class {@code BillingAddressService} is a general service to work with a
 * <i>BillingAddress</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface BillingAddressService {

	/**
	 * Sets a {@code BillingAddress} by using information from {@code UserBilling}.
	 * 
	 * @param userBilling - an address to get information from
	 * @param billingAddress - a billing address to be set
	 * @return a billing address with an information set from {@code userBilling}.
	 */
	BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
}
