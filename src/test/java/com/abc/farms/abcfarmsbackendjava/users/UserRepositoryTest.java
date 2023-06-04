package com.abc.farms.abcfarmsbackendjava.users;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.abc.farms.abcfarmsbackendjava.entities.User;
import com.abc.farms.abcfarmsbackendjava.repositories.UserRepository;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldRegisterUser() {
        var user = User.builder()
                .email("test@test.com")
                .password("12345")
                .build();

      underTest.save(user);
      
    }
    
}
