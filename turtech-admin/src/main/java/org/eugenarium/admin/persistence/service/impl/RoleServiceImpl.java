package org.eugenarium.admin.persistence.service.impl;

import org.eugenarium.admin.persistence.domain.security.Role;
import org.eugenarium.admin.persistence.repository.RoleRepository;
import org.eugenarium.admin.persistence.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class {@code RoleService} is a general service to work with a
 * <i>Role</i> type.
 * 
 * @author Yevhenii Zhyliaiev
 */
@Service
@Transactional
public class RoleServiceImpl implements RoleService {

	private RoleRepository roleRepository;

	@Autowired
	public RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	/**
	 * Retrieves a {@code Role} entity by its {@code name} field.
	 * 
	 * @param name - a name of the role to look for.
	 * @return the {@code Role} with the given {@code name} or
	 *         {@literal Optional#empty()} if none found
	 */
	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}
}