package org.eugenarium.admin.persistence.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.eugenarium.admin.persistence.domain.User;
import org.eugenarium.admin.persistence.repository.UserRepository;
import org.eugenarium.admin.persistence.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class UserServiceTest {

    @TestConfiguration
    static class UserServiceTestContextConfiguration {

        @Bean
        public UserService userService(UserRepository userRepository) {
            return new UserServiceImpl(userRepository);
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() {
        User user = new User();
        user.setUsername("Bob");
        user.setEmail("bob@gmail.com");

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
    }

    @Test
    public void whenFindByUsername_thenReturnUser() {
        String username = "Bob";
        User found = userService.findByUsername(username);

        assertThat(found.getUsername()).isEqualTo(username);
    }

    @Test
    public void whenFindByEmail_thenReturnUser() {
        String email = "bob@gmail.com";
        User found = userService.findByEmail(email);

        assertThat(found.getEmail()).isEqualTo(email);
    }

    @Test
    public void whenCreateUser_thenReturnUser() {
        User user = new User();
        user.setUsername("Bob");
        try {
            User returned = userService.createUser(user);
            assertThat(returned.getUsername()).isEqualTo(user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}