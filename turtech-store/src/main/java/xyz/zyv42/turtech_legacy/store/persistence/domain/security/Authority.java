package xyz.zyv42.turtech_legacy.store.persistence.domain.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Class {@code User} represents an authority granted to a particular customer
 * of the webstore and implements Spring's <i>GrantedAuthority</i>.
 * 
 * @see GrantedAuthority
 * 
 * @author Yevhenii Zhyliaiev
 */
public class Authority implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	/**
	 * A name of the authority, one or more of which should be granted
	 * to a particular user according to his/her roles.
	 */
	private final String authority;

	/**
	 * The only constructor for the authority which takes a name of the
	 * authority as the only parameter.
	 * 
	 * @param authority
	 */
	public Authority(String authority) {
		this.authority = authority;
	}

	/**
	 * A getter of the authority which returns a string representing its name.
	 * 
	 * @return a string representation of the authority's name.
	 */
	@Override
	public String getAuthority() {
		return authority;
	}
}
