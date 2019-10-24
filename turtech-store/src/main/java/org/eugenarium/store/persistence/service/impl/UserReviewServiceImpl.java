package org.eugenarium.store.persistence.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.eugenarium.store.persistence.domain.Product;
import org.eugenarium.store.persistence.domain.UserReview;
import org.eugenarium.store.persistence.repository.UserReviewRepository;
import org.eugenarium.store.persistence.service.UserReviewService;

/**
 * Class {@code UserReviewService} is a general service to work with a
 * <i>UserReview</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Service
@Transactional
public class UserReviewServiceImpl implements UserReviewService {

	private final UserReviewRepository userReviewRepository;

	@Autowired
	public UserReviewServiceImpl(UserReviewRepository userReviewRepository) {
		this.userReviewRepository = userReviewRepository;
	}

	/**
	 * Saves a {@code UserReview} entity to a database.
	 * 
	 * @param userReview - a user review to be saved
	 * @return the user review that has been saved.
	 */
	@Override
	public void save(UserReview userReview) {

		userReviewRepository.save(userReview);
	}

	/**
	 * Retrieves a page of {@code UserReview}'s.
	 * 
	 * @param pageRequest - contains information about the number of items per page
	 *                    and the number of the current page.
	 * @return a page of {@code UserReview}'s with the given parameters of the page.
	 */
	@Override
	public Page<UserReview> findAll(Pageable pageable) {

		return userReviewRepository.findAll(pageable);
	}

	/**
	 * Retrieves a page of {@code UserReview}'s by their {@code product} field.
	 * 
	 * @param product     - a product to which the user reviews were left.
	 * @param pageRequest - contains information about the number of items per page
	 *                    and the number of the current page.
	 * @return the page of {@code UserReview}'s with the given {@code product} and
	 *         parameters of the page.
	 */
	@Override
	public Page<UserReview> findByProduct(Product product, Pageable pageable) {

		return userReviewRepository.findByProduct(product, pageable);
	}
}
