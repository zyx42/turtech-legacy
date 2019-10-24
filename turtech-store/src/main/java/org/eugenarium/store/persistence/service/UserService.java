package org.eugenarium.store.persistence.service;

import org.eugenarium.store.persistence.domain.UserBilling;
import org.eugenarium.store.persistence.domain.UserPayment;
import org.eugenarium.store.persistence.domain.UserShipping;
import org.eugenarium.store.persistence.domain.security.PasswordResetToken;
import org.eugenarium.store.persistence.domain.User;

/**
 * Class {@code UserService} is a general service to work with a <i>User</i>
 * type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface UserService {

	/**
	 * Retrieves a {@code PasswordResetToken} by its {@code token} field.
	 * 
	 * @param token - a String representation of the password reset token to be
	 *              retrieved.
	 * @return a password reset token with the {@code token} field.
	 */
	PasswordResetToken getPasswordResetToken(final String token);

	/**
	 * Creates a new {@code PasswordResetToken} for a {@code User}.
	 * 
	 * @param user  - a user that requested a password reset token.
	 * @param token - a password reset token.
	 */
	void createPasswordResetTokenForUser(final User user, final String token);

	/**
	 * Retrieves a {@code User} entity by its {@code username} field.
	 * 
	 * @param username - a username by which the User should be searched.
	 * @return the {@code User} entity with the given {@code username} or
	 *         {@literal Optional#empty()} if none found
	 */
	User findByUsername(String username);

	/**
	 * Retrieves a {@code User} entity by its {@code email} field.
	 * 
	 * @param email - an email by which the User should be searched.
	 * @return the {@code User} entity with the given {@code email} or
	 *         {@literal Optional#empty()} if none found
	 */
	User findByEmail(String email);

	/**
	 * Retrieves a {@code User} entity by its {@code id} field.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 * @return the {@code User} entity with the given {@code id} or
	 *         {@literal Optional#empty()} if none found
	 */
	User findById(Long id);

	/**
	 * Creates a new {@code User} entity and save it to a database.
	 * 
	 * @param user - the new user to be added to a database.
	 * @return the user that was added to a database.
	 * @throws Exception
	 */
	User createUser(User user) throws Exception;

	/**
	 * Saves a {@code User} entity to a database.
	 * 
	 * @param user - a user to be saved
	 * @return the user that was saved.
	 */
	User save(User user);

	/**
	 * Updates a {@code User}'s {@code UserPayment} and {@code UserBilling} with it.
	 * 
	 * @param userBilling - the billing address to be updated with the payment
	 *                    method.
	 * @param userPayment - the payment method to be updated.
	 * @param user        - a user that wants to update his payment method and
	 *                    billing address with it.
	 */
	void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user);

	/**
	 * Updates a {@code User}'s {@code UserShipping}.
	 * 
	 * @param userShipping - the shipping address to be updated.
	 * @param user         - a user that wants to update his shipping address.
	 */
	void updateUserShipping(UserShipping userShipping, User user);

	/**
	 * Sets a {@code UserPayment} as the {@code User}'s default payment method.
	 * 
	 * @param userPaymentId - the id of the payment method to be set as user's
	 *                      default one.
	 * @param user          - a user that wants to set the payment method as his
	 *                      default one.
	 */
	void setUserDefaultPayment(Long userPaymentId, User user);

	/**
	 * Sets a {@code UserShipping} as the {@code User}'s default shipping address.
	 * 
	 * @param userPaymentId - the id of the shipping address to be set as user's
	 *                      default one.
	 * @param user          - a user that wants to set the shipping address as his
	 *                      default one.
	 */
	void setUserDefaultShipping(Long userShippingId, User user);
}
