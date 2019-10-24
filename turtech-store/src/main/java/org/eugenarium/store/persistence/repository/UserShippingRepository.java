package org.eugenarium.store.persistence.repository;

import org.eugenarium.store.persistence.domain.UserShipping;
import org.springframework.data.repository.CrudRepository;

/**
 * Class {@code UserShipppingRepository} is an interface for generic CRUD
 * operations on a repository for {@code UserShipping} type. Exposes all
 * standard CRUD operations due to extending Spring's
 * {@code CrudRepository<T, ID>}.
 * 
 * @see CrudRepository
 * @param <UserShipping> the domain type the repository manages
 * @param <Long>         the type of the id of the entity the repository manages
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface UserShippingRepository extends CrudRepository<UserShipping, Long> {
}
