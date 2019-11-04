package org.eugenarium.store.persistence.repository;

import org.eugenarium.store.persistence.domain.UserPayment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Class {@code UserPaymentRepository} is an interface for generic CRUD
 * operations on a repository for {@code UserPayment} type. Exposes all standard
 * CRUD operations due to extending Spring's {@code CrudRepository<T, ID>}.
 * 
 * @see CrudRepository
 * @param <UserPayment> the domain type the repository manages
 * @param <Long>        the type of the id of the entity the repository manages
 * 
 * @author Yevhenii Zhyliaiev
 */
@Repository
public interface UserPaymentRepository extends CrudRepository<UserPayment, Long> {
}
