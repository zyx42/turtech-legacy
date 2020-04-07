package xyz.zyv42.turtech_legacy.store.persistence.service.impl;

import org.eugenarium.store.persistence.domain.*;
import org.eugenarium.store.persistence.repository.*;
import xyz.zyv42.turtech_legacy.store.persistence.domain.User;
import xyz.zyv42.turtech_legacy.store.persistence.domain.UserBilling;
import xyz.zyv42.turtech_legacy.store.persistence.domain.UserPayment;
import xyz.zyv42.turtech_legacy.store.persistence.domain.UserShipping;
import xyz.zyv42.turtech_legacy.store.persistence.domain.security.PasswordResetToken;
import xyz.zyv42.turtech_legacy.store.persistence.repository.PasswordResetTokenRepository;
import xyz.zyv42.turtech_legacy.store.persistence.repository.UserPaymentRepository;
import xyz.zyv42.turtech_legacy.store.persistence.repository.UserRepository;
import xyz.zyv42.turtech_legacy.store.persistence.repository.UserShippingRepository;
import xyz.zyv42.turtech_legacy.store.persistence.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Class {@code UserService} is a general service to work with a <i>User</i>
 * type.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;

	private final UserPaymentRepository userPaymentRepository;

	private final UserShippingRepository userShippingRepository;

	private final PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, UserPaymentRepository userPaymentRepository,
			UserShippingRepository userShippingRepository, PasswordResetTokenRepository passwordResetTokenRepository) {
		this.userRepository = userRepository;
		this.userPaymentRepository = userPaymentRepository;
		this.userShippingRepository = userShippingRepository;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
	}

	/**
	 * Retrieves a {@code PasswordResetToken} by its {@code token} field.
	 * 
	 * @param token - a String representation of the password reset token to be
	 *              retrieved.
	 * @return a password reset token with the {@code token} field.
	 */
	@Override
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

	/**
	 * Creates a new {@code PasswordResetToken} for a {@code User}.
	 * 
	 * @param user  - a user that requested a password reset token.
	 * @param token - a password reset token.
	 */
	@Override
	public void createPasswordResetTokenForUser(final User user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(myToken);
	}

	/**
	 * Retrieves a {@code User} entity by its {@code username} field.
	 * 
	 * @param username - a username by which the User should be searched.
	 * @return the {@code User} entity with the given {@code username} or
	 *         {@literal Optional#empty()} if none found
	 */
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	/**
	 * Retrieves a {@code User} entity by its {@code email} field.
	 * 
	 * @param email - an email by which the User should be searched.
	 * @return the {@code User} entity with the given {@code email} or
	 *         {@literal Optional#empty()} if none found
	 */
	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * Retrieves a {@code User} entity by its {@code id} field.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 * @return the {@code User} entity with the given {@code id} or
	 *         {@literal Optional#empty()} if none found
	 */
	@Override
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	/**
	 * Creates a new {@code User} entity and save it to a database.
	 * 
	 * @param user - the new user to be added to a database.
	 * @return the user that was added to a database.
	 * @throws Exception
	 */
	@Override
	public User createUser(User user) {
		User localUser = userRepository.findByUsername(user.getUsername());

		if (localUser != null) {
			LOG.info("user {} already exists. Nothing will be done.", user.getUsername());
		} else {

			user.setUserShippingList(new ArrayList<>());
			user.setUserPaymentList(new ArrayList<>());

			localUser = userRepository.save(user);
		}

		return localUser;
	}

	/**
	 * Saves a {@code User} entity to a database.
	 * 
	 * @param user - a user to be saved
	 * @return the user that was saved.
	 */
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	/**
	 * Updates a {@code User}'s {@code UserPayment} and {@code UserBilling} with it.
	 * 
	 * @param userBilling - the billing address to be updated with the payment
	 *                    method.
	 * @param userPayment - the payment method to be updated.
	 * @param user        - a user that wants to update his payment method and
	 *                    billing address with it.
	 */
	@Override
	public void updateUserBilling(UserBilling userBilling, UserPayment userPayment, User user) {
		userPayment.setUser(user);
		userPayment.setUserBilling(userBilling);
		userPayment.setDefaultPayment(true);
		userBilling.setUserPayment(userPayment);
		user.getUserPaymentList().add(userPayment);
		save(user);
	}

	/**
	 * Updates a {@code User}'s {@code UserShipping}.
	 * 
	 * @param userShipping - the shipping address to be updated.
	 * @param user         - a user that wants to update his shipping address.
	 */
	@Override
	public void updateUserShipping(UserShipping userShipping, User user) {
		userShipping.setUser(user);
		userShipping.setUserShippingDefault(true);
		user.getUserShippingList().add(userShipping);
		save(user);
	}

	/**
	 * Sets a {@code UserPayment} as the {@code User}'s default payment method.
	 * 
	 * @param userPaymentId - the id of the payment method to be set as user's
	 *                      default one.
	 * @param user          - a user that wants to set the payment method as his
	 *                      default one.
	 */
	@Override
	public void setUserDefaultPayment(Long userPaymentId, User user) {
		List<UserPayment> userPaymentList = (List<UserPayment>) userPaymentRepository.findAll();

		for (UserPayment userPayment : userPaymentList) {
			if (userPayment.getId().longValue() == userPaymentId.longValue()) {
				userPayment.setDefaultPayment(true);
				userPaymentRepository.save(userPayment);
			} else {
				userPayment.setDefaultPayment(false);
				userPaymentRepository.save(userPayment);
			}
		}
	}

	/**
	 * Sets a {@code UserShipping} as the {@code User}'s default shipping address.
	 * 
	 * @param userPaymentId - the id of the shipping address to be set as user's
	 *                      default one.
	 * @param user          - a user that wants to set the shipping address as his
	 *                      default one.
	 */
	@Override
	public void setUserDefaultShipping(Long userShippingId, User user) {
		List<UserShipping> userShippingList = (List<UserShipping>) userShippingRepository.findAll();

		for (UserShipping userShipping : userShippingList) {
			if (userShipping.getId().longValue() == userShippingId.longValue()) {
				userShipping.setUserShippingDefault(true);
				userShippingRepository.save(userShipping);
			} else {
				userShipping.setUserShippingDefault(false);
				userShippingRepository.save(userShipping);
			}
		}
	}
}
