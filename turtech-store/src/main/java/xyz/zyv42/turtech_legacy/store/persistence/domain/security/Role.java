package xyz.zyv42.turtech_legacy.store.persistence.domain.security;

import xyz.zyv42.turtech_legacy.store.persistence.domain.User;
import lombok.Data;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Class {@code Role} represents a role which a particular user has according to
 * which the user gets respective authorities.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Entity
@Data
@Table(name = "role", schema = "turtech")
public class Role implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	/**
	 * A name of the role, according to which the user is granted his authorities.
	 */
	@Column(name = "name")
	private String name;

	/**
	 * A set of users to which a particular role belongs. A single user
	 * can have multiple roles and a single role can encompass multiple users.
	 */
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<>();

	/**
	 * Returns the hash code value for this Role. It uses a prime number,
	 * <tt>id</tt> and <tt>name</tt> as means to calculate the hash code.
	 *
	 * @return the hash code value for this User
	 *
	 * @see Object#hashCode()
	 * @see Object#equals(Object)
	 */
	@Override
	public int hashCode() {
		return 7 * name.hashCode();
	}

	/**
	 * Indicates whether some other object is &quot;equal to&quot; this Role. This
	 * method can return <tt>true</tt> <i>only</i> if the specified object is also a
	 * Role and it has the same <tt>id</id> and <tt>name</tt>.
	 * 
	 * @param obj the reference object with which to compare.
	 * @return <code>true</code> only if the specified object is also a Role. and it
	 *         it has the same <tt>id</id> and <tt>name</tt>.
	 * @see Object#equals(Object)
	 * @see Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Role))
			return false;
		Role other = (Role) obj;
		return name.equals(other.getName());
	}
}
