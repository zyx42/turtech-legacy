package org.eugenarium.store.persistence.service;

import org.eugenarium.store.persistence.domain.UserShipping;

/**
 * Class {@code UserShippingService} is a general service to work with a
 * <i>UserShipping</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface UserShippingService {

	/**
	 * Retrieves a {@code UserShipping} entity by its {@code id} field.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 * @return the {@code UserShipping} entity with the given {@code id} or
	 *         {@literal Optional#empty()} if none found
	 */
	UserShipping findById(Long id);

	/**
	 * Deletes a {@code UserShipping} entity with the given {@code id}.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 */
	void deleteById(Long id);
}
