package org.eugenarium.store.persistence.repository;

import org.eugenarium.store.persistence.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code OrderRepository} is an interface for generic CRUD operations
 * on a repository for {@code Order} type. Exposes all standard CRUD operations
 * due to extending Spring's {@code CrudRepository<T, ID>}.
 * 
 * @see CrudRepository
 * @param <Order> the domain type the repository manages
 * @param <Long>  the type of the id of the entity the repository manages
 * 
 * @author Yevhenii Zhyliaiev
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
