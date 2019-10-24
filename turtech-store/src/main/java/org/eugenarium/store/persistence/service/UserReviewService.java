package org.eugenarium.store.persistence.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.eugenarium.store.persistence.domain.Product;
import org.eugenarium.store.persistence.domain.UserReview;

/**
 * Class {@code UserReviewService} is a general service to work with a
 * <i>UserReview</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface UserReviewService {

	/**
	 * Saves a {@code UserReview} entity to a database.
	 * 
	 * @param userReview - a user review to be saved
	 * @return the user review that has been saved.
	 */
	void save(UserReview userReview);

	/**
	 * Retrieves a page of {@code UserReview}'s.
	 * 
	 * @param pageRequest - contains information about the number of items per page
	 *                    and the number of the current page.
	 * @return a page of {@code UserReview}'s with the given parameters of the page.
	 */
	Page<UserReview> findAll(Pageable pageable);

	/**
	 * Retrieves a page of {@code UserReview}'s by their {@code product} field.
	 * 
	 * @param product     - a product to which the user reviews were left.
	 * @param pageRequest - contains information about the number of items per page
	 *                    and the number of the current page.
	 * @return the page of {@code UserReview}'s with the given {@code product} and
	 *         parameters of the page.
	 */
	Page<UserReview> findByProduct(Product product, Pageable pageable);
}
