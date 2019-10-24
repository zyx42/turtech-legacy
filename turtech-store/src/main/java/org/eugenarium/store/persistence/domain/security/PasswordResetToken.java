package org.eugenarium.store.persistence.domain.security;

import org.eugenarium.store.persistence.domain.User;
import lombok.Data;

import javax.persistence.*;

import java.time.LocalDateTime;

/**
 * Class {@code PasswordResetToken} represents password reset token which is
 * used to reclaim customer's account when he forgot the password and provided
 * an email.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Entity
@Data
@Table(name = "password_reset_token", schema = "turtech")
public class PasswordResetToken {

	// The amount of minutes until the token will expire.
	private static final int EXPIRATION = 60 * 24;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	/**
	 * A string representation of the token, which is basically a pseudo-random
	 * string of characters which is generated on request.
	 */
	@Column(name = "token")
	private String token;

	/**
	 * A user by who the token was requested.
	 */
	@OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "user_id")
	private User user;

	/**
	 * A date on which the token will expire.
	 */
	@Column(name = "expiry_date")
	private LocalDateTime expiryDate;

	/**
	 * A standard constructor without parameters.
	 */
	public PasswordResetToken() {
	}

	/**
	 * A constructor which takes a {@code token} and {@code user} as parameters.
	 * 
	 * @param token
	 * @param user
	 */
	public PasswordResetToken(final String token, final User user) {
		super();

		this.token = token;
		this.user = user;
		this.expiryDate = calculateExpiryDate();
	}

	/**
	 * A method that returns a date on which the token will expire. To calculate the
	 * expiry date it adds to a current moment of time an amount of minutes set in a
	 * constant.
	 * 
	 * @return a date on which the token will expire.
	 */
	private LocalDateTime calculateExpiryDate() {
		return LocalDateTime.now().plusMinutes(PasswordResetToken.EXPIRATION);
	}

	/**
	 * A method which takes a string representation of a token and updates its
	 * expiry date by using {@code #calculateExpiryDate()}.
	 * 
	 * @param token
	 */
	public void updateToken(final String token) {
		this.token = token;
		this.expiryDate = calculateExpiryDate();
	}

	/**
	 * Returns the hash code value for this PasswordResetToken. It uses a prime
	 * number, <tt>id</tt>, <tt>token</tt> and <tt>expiryDate</tt> as means to
	 * calculate the hash code.
	 *
	 * @return the hash code value for this PasswordResetToken
	 *
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	@Override
	public int hashCode() {
		return 7 * (int) id.longValue() * token.hashCode() * expiryDate.hashCode();
	}

	/**
	 * Indicates whether some other object is &quot;equal to&quot; this
	 * PasswordResetToken. This method can return <tt>true</tt> <i>only</i> if the
	 * specified object is also a PasswordResetToken and it has the same
	 * <tt>id</id>, <tt>token</tt> and <tt>expiryDate</tt>.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also a
	 *         PasswordResetToken. and it it has the same
	 *         <tt>id</id>, <tt>token</tt> and <tt>expiryDate</tt>.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PasswordResetToken))
			return false;
		PasswordResetToken other = (PasswordResetToken) obj;
		return id.equals(other.getId()) && token.equals(other.getToken()) && expiryDate.equals(other.getExpiryDate());
	}
}
