package com.abc.farms.abcfarmsbackendjava.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import com.abc.farms.abcfarmsbackendjava.entities.User;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    private User user;

    @BeforeEach
    void setUp()  {
        user = User.builder()
                .email("test@test.com")
                .firstName("John")
                .lastName("Doe")
                .password("12345678")
                .phone("09084880043")
                .build();

        userRepository.save(user);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void itShouldFindByEmail() {
       User foundUser = userRepository.findByEmail(user.getEmail()).get();

       assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    void itShouldNotFindByEmail() {
        Optional<User> foundUser = userRepository.findByEmail("not_found@gmail.com");

        assertThat(foundUser).isEmpty();
    }
    
}

