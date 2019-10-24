package org.eugenarium.store.persistence.service.impl;

import org.eugenarium.store.persistence.domain.BillingAddress;
import org.eugenarium.store.persistence.domain.UserBilling;
import org.eugenarium.store.persistence.service.BillingAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class {@code BillingAddressService} is a general service to work with a
 * <i>BillingAddress</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Service
@Transactional
public class BillingAddressServiceImpl implements BillingAddressService {

	/**
	 * Sets a {@code BillingAddress} by using information from {@code UserBilling}.
	 * 
	 * @param userbilling - an address to get information from
	 * @param billingAddress - a billing address to be set
	 * @return a billing address with an information set from {@code userBilling}.
	 */
	public BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress) {
		billingAddress.setBillingAddressName(userBilling.getUserBillingName());
		billingAddress.setBillingAddressStreet1(userBilling.getUserBillingStreet1());
		billingAddress.setBillingAddressStreet2(userBilling.getUserBillingStreet2());
		billingAddress.setBillingAddressCity(userBilling.getUserBillingCity());
		billingAddress.setBillingAddressCountry(userBilling.getUserBillingCountry());
		billingAddress.setBillingAddressZipcode(userBilling.getUserBillingZipcode());

		return billingAddress;
	}
}
