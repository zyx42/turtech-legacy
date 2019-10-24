package org.eugenarium.admin.persistence.service;

import org.eugenarium.admin.persistence.domain.security.Role;

/**
 * Class {@code RoleService} is a general service to work with a
 * <i>Role</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
public interface RoleService {

	/**
	 * Retrieves a {@code Role} entity by its {@code name} field.
	 * 
	 * @param name - a name of the role to look for.
	 * @return the {@code Role} with the given {@code name} or
	 *         {@literal Optional#empty()} if none found
	 */
	Role findByName(String name);
}