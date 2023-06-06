package com.abc.farms.abcfarmsbackendjava.services.implementations;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abc.farms.abcfarmsbackendjava.config.JwtService;
import com.abc.farms.abcfarmsbackendjava.entities.User;
import com.abc.farms.abcfarmsbackendjava.interfaces.user.Roles;
import com.abc.farms.abcfarmsbackendjava.repositories.UserRepository;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.BadRequestError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.ConflictError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.LoginRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.RegisterRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.LoginResponse;
import com.abc.farms.abcfarmsbackendjava.services.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public boolean register(RegisterRequest request) throws ConflictError {
        Optional<User> userExists = userRepository.findByEmail(request.getEmail());

        if (userExists.isPresent()) {
            throw new ConflictError("This email already exists");
        }

        logger.info("User is not present");

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(Roles.USER)
                .build();

        userRepository.save(user);

        return true;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws BadRequestError {
        var userDetails = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestError("Email or password is incorrect"));

        logger.info("Email is correct");

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), userDetails.getPassword());

        if (!passwordMatches) {
            throw new BadRequestError("Email or password is incorrect");
        }

        logger.info("Password is correct");

        var token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
                .token(token)
                .build();
    }

}
