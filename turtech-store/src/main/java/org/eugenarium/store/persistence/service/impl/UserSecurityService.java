package org.eugenarium.store.persistence.service.impl;

import org.eugenarium.store.persistence.domain.User;
import org.eugenarium.store.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class {@code UserService} is a security service to work with a <i>User</i>
 * type and used for authorization.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Service
@Transactional
public class UserSecurityService implements UserDetailsService {

	private final UserRepository userRepository;

	@Autowired
	public UserSecurityService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Retrieves a {@code User} entity by its {@code username} field.
	 * 
	 * @param username - a username by which the User should be searched.
	 * @return the {@code User} entity with the given {@code username} or
	 *         {@literal Optional#empty()} if none found
	 * @throws UsernameNotFoundException - an exception which is thrown in case no
	 *                                   user was found by a given name.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException("Username not found");
		}

		return user;
	}
}
