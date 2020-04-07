package xyz.zyv42.turtech_legacy.admin.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import xyz.zyv42.turtech_legacy.admin.persistence.domain.User;
import xyz.zyv42.turtech_legacy.admin.persistence.domain.security.Role;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@ComponentScan(basePackages = {"org.eugenarium.admin.persistence.repository"})
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void whenFindByUsername_thenReturnUser() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("j@gmail.com");
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);
        roles.add(role);
        user.setRoles(roles);
        entityManager.persistAndFlush(user);

        User found = userRepository.findByUsername("John");
        assertThat(found.getUsername()).isEqualTo(user.getUsername());
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("j@gmail.com");
        List<Role> roles = new ArrayList<>();
        Role role = new Role();
        role.setName("ROLE_USER");
        roleRepository.save(role);
        roles.add(role);
        user.setRoles(roles);
        entityManager.persistAndFlush(user);

        User found = userRepository.findByEmail("j@gmail.com");
        assertThat(found.getEmail()).isEqualTo(user.getEmail());
    }
}