package com.abc.farms.abcfarmsbackendjava.services.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.abc.farms.abcfarmsbackendjava.config.JwtService;
import com.abc.farms.abcfarmsbackendjava.entities.User;
import com.abc.farms.abcfarmsbackendjava.interfaces.user.Roles;
import com.abc.farms.abcfarmsbackendjava.repositories.UserRepository;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.NotFoundError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.LoginRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.RegisterRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.LoginResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public void register(RegisterRequest request) {

               
        var user = User.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .firstName(request.getFirstName())
        .lastName(request.getLastName())
        .role(Roles.USER)
        .build();

        userRepository.save(user);

        return;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var userDetails = userRepository.findByEmail(request.getEmail())
        .orElseThrow();

        var token = jwtService.generateToken(userDetails);

        return LoginResponse.builder()
        .token(token)
        .build();
    }
    
}
