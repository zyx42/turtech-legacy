package org.eugenarium.admin.persistence.service.impl;

import org.eugenarium.admin.persistence.domain.User;
import org.eugenarium.admin.persistence.repository.UserRepository;
import org.eugenarium.admin.persistence.service.UserService;
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
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
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
	 * Creates a new {@code User} entity and save it to a database.
	 * 
	 * @param user - the new user to be added to a database.
	 * @return the user that was added to a database.
	 * @throws Exception
	 */
	@Override
	public User createUser(User user) {
		User localUser = userRepository.findByUsername(user.getUsername());

		if(localUser != null) {
			LOG.info("user {} already exists. Nothing will be done.", user.getUsername());
		} else {

			user.setUserShippingList(new ArrayList<>());
			user.setUserPaymentList(new ArrayList<>());

			localUser = userRepository.save(user);
		}

		return localUser;
	}

	/**
	 * Persists a {@code User} entity to a database or merges it.
	 * 
	 * @param user - a user to be saved
	 * @return the user that was saved.
	 */
	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	/**
	 * Retrieves a list of all {@code User}'s.
	 * 
	 * @return a list of all {@code User}'s.
	 */
	public List<User> findAll() {
		return (List<User>) userRepository.findAll();
	}

	/**
	 * Retrieves a {@code User} entity by its {@code id} field.
	 * 
	 * @param {@code id} must not be {@literal null}.
	 * @return the {@code User} entity with the given {@code id} or
	 *         {@literal Optional#empty()} if none found
	 */
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	/**
    * Find a {@code User} entity by its {@code id} field and deletes it from the database.
    * 
    * @param {@code id} must not be {@literal null}.
    */
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}
}