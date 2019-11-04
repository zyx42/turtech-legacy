package org.eugenarium.store.persistence.repository;

import org.eugenarium.store.persistence.domain.User;
import org.eugenarium.store.persistence.domain.security.PasswordResetToken;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.stream.Stream;

/**
 * Class {@code PasswordResetTokenRepository} is an interface for generic CRUD
 * operations on a repository for {@code PasswordResetToken} type. Exposes all standard
 * CRUD operations due to extending Spring's {@code CrudRepository<T, ID>}.
 * 
 * @see CrudRepository
 * @param <PasswordResetToken> the domain type the repository manages
 * @param <Long>     the type of the id of the entity the repository manages
 * 
 * @author Yevhenii Zhyliaiev
 */
@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long> {

	/**
	 * Retrieves a {@code PasswordResetToken} entity by its {@code token} field.
	 *
	 * @param {@code token} must not be {@literal null}.
	 * @return the {@code PasswordResetToken} entity with the given {@code token} or {@literal Optional#empty()} if none found
	 */
    PasswordResetToken findByToken(String token);
    
	/**
	 * Retrieves a {@code PasswordResetToken} entity by its {@code user} field.
	 *
	 * @param {@code user} must not be {@literal null}.
	 * @return the {@code PasswordResetToken} entity with the given {@code token} or {@literal Optional#empty()} if none found
	 */
    PasswordResetToken findByUser(User user);

	/**
	 * Retrieves a stream of {@code PasswordResetToken}'s that has expired.
	 * Processing data with a <i>Stream</i> requires to close the <i>Stream> when you
	 * finish it with either {@code #close()} or by using <i>try-with-resources</i>.
	 *
	 * @param {@code now} must not be {@literal null}.
	 * @return a stream of {@code PasswordResetToken}'s that have expired
	 */
    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Timestamp now);

	/**
	 * Deletes all the {@code PasswordResetToken}'s that have expired.
	 *
	 * @param the current moment of time
	 */
    @Modifying
    @Query("delete from PasswordResetToken t where t.expiryDate <= ?1")
    void deleteAllExpiredSince(Timestamp now);
}
