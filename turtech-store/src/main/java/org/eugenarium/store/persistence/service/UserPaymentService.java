package org.eugenarium.store.persistence.service;

import org.eugenarium.store.persistence.domain.UserPayment;

/**
 * Class {@code UserPaymentService} is a general service to work with a
 * <i>UserPayment</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface UserPaymentService {

	/**
	 * Retrieves a {@code UserPayment} entity by its {@code id} field.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 * @return the {@code UserPayment} entity with the given {@code id} or
	 *         {@literal Optional#empty()} if none found
	 */
	UserPayment findById(Long id);

	/**
	 * Deletes a {@code UserPayment} entity with the given {@code id}.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 */
	void deleteById(Long id);
}
