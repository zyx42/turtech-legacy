package org.eugenarium.admin.persistence.domain;

import org.eugenarium.admin.persistence.domain.security.Authority;
import org.eugenarium.admin.persistence.domain.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Class {@code User} represents a customer of the webstore and implements
 * Spring's <i>UserDetails</i>.
 * 
 * @see UserDetails
 * 
 * @author Yevhenii Zhyliaiev
 */
@Entity
@Data
@Table(name = "user", schema = "turtech")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	/**
	 * A nickname which is used by the user to log into his/her account on the
	 * webstore's website.
	 */
	@Column(name = "username", nullable = false)
	private String username;

	/**
	 * A password which is user by the user to log into his/her account on the
	 * webstore's website.
	 */
	@Column(name = "password", nullable = false)
	private String password;

	/**
	 * A first name of the user. This is optional information.
	 */
	@Column(name = "first_name")
	private String firstName;

	/**
	 * A last name of the user. This is optional information.
	 */
	@Column(name = "last_name")
	private String lastName;

	/**
	 * An email which is used by the user to retrieve notifications, order
	 * confirmations, restore password an all of the kind.
	 */
	@Column(name = "email", nullable = false)
	private String email;

	/**
	 * A phone number of the user. This is optional information.
	 */
	@Column(name = "phone")
	private String phone;

	/**
	 * A field which specifies if the user account is enabled or not. It
	 * can be disabled for various reasons, like expiration or ban.
	 */
	@Column(name = "enabled")
	private boolean enabled = true;

	/**
	 * A shopping cart which belongs to the user.
	 */
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
	private ShoppingCart shoppingCart;

	/**
	 * A list of shipping information sets that were saved by the user
	 * on his/her account for later usage.
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<UserShipping> userShippingList;

	/**
	 * A list of payment information sets that were saved by the user
	 * on his/her account for later usage.
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<UserPayment> userPaymentList;

	/**
	 * A list of orders that the user has placed.
	 */
	@OneToMany(mappedBy = "user")
	private List<Order> orderList;

	/**
	 * A list of reviews that the user has left.
	 */
	@OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserReview> userReviewList;

	/**
	 * A set of roles according to which the user is granted his/her authorities
	 * on the webstore's website.
	 */
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
	@JsonIgnore
	private List<Role> roles = new ArrayList<>();

	/**
	 * An auditing field, which specifies the date on which the product was added.
	 * It's filled automatically.
	 */
	@Column(name = "created_date", updatable = false)
	@CreatedDate
	@JsonIgnore
	private LocalDateTime createdDate;

	/**
	 * An auditing field, which specifies the person responsible for adding the product.
	 * It's filled automatically
	 */
	@Column(name = "created_by", updatable = false)
	@CreatedBy
	@JsonIgnore
	private String createdBy;

	/**
	 * An auditing field, which specified the date on which the product was last modified.
	 * It's filled automatically
	 */
	@Column(name = "last_modified_date")
	@LastModifiedDate
	@JsonIgnore
	private LocalDateTime lastModifiedDate;

	/**
	 * An auditing field, which specified the person responsible for the last changes
	 * to the product information. It's filled automatically.
	 */
	@Column(name = "last_modified_by")
	@LastModifiedBy
	@JsonIgnore
	private String lastModifiedBy;

	/**
	 * Return the authorities granted to the user. Cannot return <code>null</code>.
	 * 
	 * @return the authorities, sorted by natural key (never <code>null</code>
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		roles.forEach(role -> authorities.add(new Authority(role.getName())));

		return authorities;
	}

	/**
	 * Indicates whether the user's account has expired. An expired account cannot be
	 * authenticated.
	 * 
	 * @return <code>true</code> if the user's account is valid (i.e. non-expired),
	 * <code>false</code> if no longer valid (i.e. expired).
	 */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Indicated whether the user is locked or unlocked. A locked user
	 * cannot be authenticated.
	 * 
	 * @return <code>true</code> if the user is not locked, <code>false</code>
	 * otherwise.
	 */
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Indicates whether the user's credentials (password) has expired. Expired
	 * credentials prevent authentication.
	 * 
	 * @return <code>true</code> if the user's credentials are valid (i.e. non-expired),
	 * <code>false</code> if no longer valid (i.e. expired)
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/**
	 * Returns the hash code value for this User. It uses a prime number,
	 * <tt>id</tt>, <tt>username</tt> and <tt>email</tt> as means to calculate the hash code.
	 *
	 * @return the hash code value for this User
	 *
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	@Override
	public int hashCode() {
		return 7 * (int) id.longValue() * username.hashCode() * email.hashCode();
	}

	/**
	 * Indicates whether some other object is &quot;equal to&quot; this User.
	 * This method can return <tt>true</tt> <i>only</i> if the specified object is
	 * also a User and it has the same
	 * <tt>id</id>, <tt>username</tt> and <tt>email</tt>.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also a User.
	 *         and it it has the same
	 *         <tt>id</id>, <tt>username</tt> and <tt>email</tt>.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		return id.equals(other.getId()) && username.equals(other.getUsername()) && email.equals(other.getEmail());
	}

	@Override
	public String toString() {
		return "id= " + id + ", username=" + username;
	}
}