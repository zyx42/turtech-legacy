package xyz.zyv42.turtech_legacy.store.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.zyv42.turtech_legacy.store.persistence.domain.Product;
import xyz.zyv42.turtech_legacy.store.persistence.domain.UserReview;

/**
 * Class {@code UserReviewRepository} is an interface for generic CRUD
 * operations on a repository for {@code UserReview} type. Exposes all standard
 * CRUD operations due to extending Spring's {@code JpaRepository<T, ID>} and
 * provides {@code Page} return type.
 * 
 * @see JpaRepository
 * @param <UserReview> the domain type the repository manages
 * @param <Long>       the type of the id of the entity the repository manages
 * 
 * @author Yevhenii Zhyliaiev
 */
@Repository
public interface UserReviewRepository extends JpaRepository<UserReview, Long> {

	/**
	 * Retrieves a page of all {@code Product}'s.
	 *
	 * @param {@code pageable} specifies a number of items per page and a number of
	 * the page to return.
	 * @return a page of all {@code UserReview}'s
	 */
	Page<UserReview> findAll(Pageable pageable);

	/**
	 * Retrieves a page of {@code UserReview}'s by the {@code product} they belong
	 * to.
	 *
	 * @param {@code product} must not be {@literal null}.
	 * @param {@code pageable} specifies a number of items per page and a number of
	 * the page to return.
	 * @return a page of {@code Product}'s by the given {@code product}
	 */
	Page<UserReview> findByProduct(Product product, Pageable pageable);
}
