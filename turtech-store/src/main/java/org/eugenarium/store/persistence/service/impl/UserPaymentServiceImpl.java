package org.eugenarium.store.persistence.service.impl;

import org.eugenarium.store.persistence.domain.UserPayment;
import org.eugenarium.store.persistence.repository.UserPaymentRepository;
import org.eugenarium.store.persistence.service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class {@code UserPaymentService} is a general service to work with a
 * <i>UserPayment</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Service
@Transactional
public class UserPaymentServiceImpl implements UserPaymentService {

	private final UserPaymentRepository userPaymentRepository;

	@Autowired
	public UserPaymentServiceImpl(UserPaymentRepository userPaymentRepository) {
		this.userPaymentRepository = userPaymentRepository;
	}

	/**
	 * Retrieves a {@code UserPayment} entity by its {@code id} field.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 * @return the {@code UserPayment} entity with the given {@code id} or
	 *         {@literal Optional#empty()} if none found
	 */
	public UserPayment findById(Long id) {
		return userPaymentRepository.findById(id).get();
	}

	/**
	 * Deletes a {@code UserPayment} entity with the given {@code id}.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 */
	public void deleteById(Long id) {
		userPaymentRepository.deleteById(id);
	}
}
