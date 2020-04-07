package xyz.zyv42.turtech_legacy.store.persistence.repository;

import xyz.zyv42.turtech_legacy.store.persistence.domain.security.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code RoleRepository} is an interface for generic CRUD operations
 * on a repository for {@code Role} type. Exposes all standard CRUD operations
 * due to extending Spring's {@code CrudRepository<T, ID>}.
 * 
 * @see CrudRepository
 * @param <Role> the domain type the repository manages
 * @param <Long> the type of the id of the entity the repository manages
 * 
 * @author Yevhenii Zhyliaiev
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

	/**
	 * Retrieves a {@code Role} entity by its {@code name} field.
	 *
	 * @param {@code name} must not be {@literal null}.
	 * @return the {@code Role} entity with the given {@code name} or
	 *         {@literal Optional#empty()} if none found
	 */
	Role findByName(String name);
}
