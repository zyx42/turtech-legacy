package xyz.zyv42.turtech_legacy.admin.persistence.service;

import xyz.zyv42.turtech_legacy.admin.persistence.domain.User;

import java.util.List;

/**
 * Class {@code UserService} is a general service to work with a <i>User</i>
 * type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface UserService {

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
	 * Retrieves a list of all {@code User}'s.
	 * 
	 * @return a list of all {@code User}'s.
	 */
	List<User> findAll();

	/**
	 * Retrieves a {@code User} entity by its {@code id} field.
	 * 
	 * @param id must not be {@literal null}.
	 * @return the {@code User} entity with the given {@code id} or
	 *         {@literal Optional#empty()} if none found
	 */
	User findById(Long id);

	/**
    * Find a {@code User} entity by its {@code id} field and deletes it from the database.
    * 
    * @param user - the user to delete.
    */
	void delete(User user);
}