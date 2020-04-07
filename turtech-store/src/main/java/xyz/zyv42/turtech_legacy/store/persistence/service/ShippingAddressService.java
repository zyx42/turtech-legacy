package xyz.zyv42.turtech_legacy.store.persistence.service;

import xyz.zyv42.turtech_legacy.store.persistence.domain.UserShipping;
import xyz.zyv42.turtech_legacy.store.persistence.domain.ShippingAddress;

/**
 * Class {@code ShippingAddressService} is a general service to work with a
 * <i>ShippingAddress</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface ShippingAddressService {

	/**
	 * Sets a {@code ShippingAddress} by using information from {@code UserShipping}.
	 * 
	 * @param userShipping - user shipping address to get information from.
	 * @param shippingAddress - shipping address to be set.
	 * @return a shipping address with an information set from {@code userShipping}.
	 */
    ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
