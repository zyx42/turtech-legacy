package org.eugenarium.store.persistence.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

/**
 * Class {@code UserReview} represents a user review which is left by a
 * particular user concerning a particular product.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Entity
@Data
@Table(name = "user_review", schema = "turtech")
public class UserReview implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	/**
	 * A text which the user review contains.
	 */
	@Column(name = "text")
	private String text;

	/**
	 * A time stamp of when the user review was left.
	 */
	@Column(name = "timestamp")
	private LocalDateTime timestamp;

	/**
	 * A user that left the review.
	 */
	@ManyToOne
	private User user;

	/**
	 * A product concerning which the review was left.
	 */
	@ManyToOne
	private Product product;

	/**
	 * Returns the hash code value for this UserReview. It uses a prime number,
	 * <tt>id</tt> and <tt>text</tt> as means to calculate the hash code.
	 *
	 * @return the hash code value for this UserReview
	 *
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	@Override
	public int hashCode() {
		return 7 * (int) id.longValue() * text.hashCode();
	}

	/**
	 * Indicates whether some other object is &quot;equal to&quot; this UserReview.
	 * This method can return <tt>true</tt> <i>only</i> if the specified object is
	 * also a UserReview and it has the same <tt>id</id> and <tt>text</tt>.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also a UserReview
	 *         and it it has the same <tt>id</id> and <tt>text</tt>.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserReview))
			return false;
		UserReview other = (UserReview) obj;
		return id.equals(other.getId()) && text.equals(other.getText());
	}
}
