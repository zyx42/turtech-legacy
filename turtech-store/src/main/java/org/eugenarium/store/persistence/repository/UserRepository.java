package org.eugenarium.store.persistence.repository;

import org.eugenarium.store.persistence.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code UserRepository} is an interface for generic CRUD operations
 * on a repository for {@code User} type. Exposes all standard CRUD operations
 * due to extending Spring's {@code CrudRepository<T, ID>}.
 * 
 * @see CrudRepository
 * @param <User> the domain type the repository manages
 * @param <Long> the type of the id of the entity the repository manages
 * 
 * @author Yevhenii Zhyliaiev
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * Retrieves a {@code User} entity by its {@code username} field.
	 *
	 * @param {@code username} must not be {@literal null}.
	 * @return the {@code User} entity with the given {@code username} or
	 *         {@literal Optional#empty()} if none found
	 */
	User findByUsername(String username);

	/**
	 * Retrieves a {@code User} entity by its {@code email} field.
	 *
	 * @param {@code email} must not be {@literal null}.
	 * @return the {@code User} entity with the given {@code email} or
	 *         {@literal Optional#empty()} if none found
	 */
	User findByEmail(String email);
}
