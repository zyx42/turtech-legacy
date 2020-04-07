package xyz.zyv42.turtech_legacy.store.persistence.service.impl;

import xyz.zyv42.turtech_legacy.store.persistence.domain.UserShipping;
import xyz.zyv42.turtech_legacy.store.persistence.repository.UserShippingRepository;
import xyz.zyv42.turtech_legacy.store.persistence.service.UserShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class {@code UserShippingService} is a general service to work with a
 * <i>UserShipping</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Service
@Transactional
public class UserShippingServiceImpl implements UserShippingService {

	private final UserShippingRepository userShippingRepository;

	@Autowired
	public UserShippingServiceImpl(UserShippingRepository userShippingRepository) {
		this.userShippingRepository = userShippingRepository;
	}

	/**
	 * Retrieves a {@code UserShipping} entity by its {@code id} field.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 * @return the {@code UserShipping} entity with the given {@code id} or
	 *         {@literal Optional#empty()} if none found
	 */
	public UserShipping findById(Long id) {
		return userShippingRepository.findById(id).get();
	}

	/**
	 * Deletes a {@code UserShipping} entity with the given {@code id}.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 */
	public void deleteById(Long id) {
		userShippingRepository.deleteById(id);
	}
}
