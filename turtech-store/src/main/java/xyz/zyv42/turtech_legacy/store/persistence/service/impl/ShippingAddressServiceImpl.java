package xyz.zyv42.turtech_legacy.store.persistence.service.impl;

import xyz.zyv42.turtech_legacy.store.persistence.domain.ShippingAddress;
import xyz.zyv42.turtech_legacy.store.persistence.domain.UserShipping;
import xyz.zyv42.turtech_legacy.store.persistence.service.ShippingAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class {@code ShippingAddressService} is a general service to work with a
 * <i>ShippingAddress</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Service
@Transactional
public class ShippingAddressServiceImpl implements ShippingAddressService {

	/**
	 * Sets a {@code ShippingAddress} by using information from {@code UserShipping}.
	 * 
	 * @param userShipping - user shipping address to get information from.
	 * @param shippingAddress - shipping address to be set.
	 * @return a shipping address with an information set from {@code userShipping}.
	 */
    public ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress) {
        shippingAddress.setShippingAddressName(userShipping.getUserShippingName());
        shippingAddress.setShippingAddressStreet1(userShipping.getUserShippingStreet1());
        shippingAddress.setShippingAddressStreet2(userShipping.getUserShippingStreet2());
        shippingAddress.setShippingAddressCity(userShipping.getUserShippingCity());
        shippingAddress.setShippingAddressCountry(userShipping.getUserShippingCountry());
        shippingAddress.setShippingAddressZipcode(userShipping.getUserShippingZipcode());

        return shippingAddress;
    }
}
