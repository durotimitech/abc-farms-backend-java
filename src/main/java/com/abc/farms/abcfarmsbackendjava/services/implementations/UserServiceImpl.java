package com.abc.farms.abcfarmsbackendjava.services.implementations;

import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.abc.farms.abcfarmsbackendjava.config.JwtService;
import com.abc.farms.abcfarmsbackendjava.entities.User;
import com.abc.farms.abcfarmsbackendjava.interfaces.user.Roles;
import com.abc.farms.abcfarmsbackendjava.repositories.UserRepository;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.BadRequestError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.errors.ConflictError;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.ChangePasswordRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.LoginRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.RegisterRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.ResendVerificationRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.requestMappings.users.ResetPasswordRequest;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.LoginResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.RegisterResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.ResendVerificationEmailResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.users.ResetPasswordResponse;
import com.abc.farms.abcfarmsbackendjava.services.interfaces.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    HttpServletRequest request;

    @Override
    public RegisterResponse register(RegisterRequest request) throws ConflictError {
        RegisterResponse response = new RegisterResponse();
        Optional<User> userExists = userRepository.findByEmail(request.getEmail());

        if (userExists.isPresent()) {
            throw new ConflictError("This email already exists");
        }

        logger.info("User is not present");

        // Generate verification code
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        String emailVerificationCode = request.getEmail() + String.valueOf(code);

        // TODO: Send to email
        String verificationLink = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/users/verify-email")
                .queryParam("emailVerificationCode", emailVerificationCode)
                .toUriString();

        response.setVerificationCode(verificationLink);

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phone(request.getPhone())
                .emailVerificationCode(emailVerificationCode)
                .role(Roles.USER)
                .isEmailVerified(false)
                .build();

        userRepository.save(user);

        return response;
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
                .isEmailVerified(userDetails.isEmailVerified())
                .firstName(userDetails.getFirstName())
                .role(userDetails.getRole())
                .build();
    }

    @Override
    public boolean verifyEmail(String emailVerificationCode) throws BadRequestError {
        Optional<User> user = userRepository.findByEmailVerificationCode(emailVerificationCode);

        if (user.isEmpty()) {
            throw new BadRequestError("Invalid verification code");
        }

        if (user.get().isEmailVerified()) {
            throw new BadRequestError("Email already verified");
        }

        User verifiedUser = user.get();
        verifiedUser.setEmailVerified(true);

        userRepository.save(verifiedUser);

        return true;
    }

    @Override
    public ResendVerificationEmailResponse resendVerificationEmail(@Valid ResendVerificationRequest request) throws BadRequestError {
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isEmpty()) {
            throw new BadRequestError("Invalid email");
        }

        if (user.get().isEmailVerified()) {
            throw new BadRequestError("Email already verified");
        }

        String verificationLink = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/users/verify-email")
                .queryParam("emailVerificationCode", user.get().getEmailVerificationCode())
                .toUriString();

        logger.info(verificationLink);

        return ResendVerificationEmailResponse.builder()
                .verificationCode(verificationLink)
                .build();
    }

    @Override
    public ResetPasswordResponse resetPassword(@Valid ResetPasswordRequest request) throws BadRequestError {
        Optional<User> user = userRepository.findByEmail(request.getEmail());

        if (user.isEmpty()) {
            throw new BadRequestError("Invalid email");
        }

        // Generate new 6 digit password
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        String newPassword = String.valueOf(code);

        user.get().setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user.get());

        return ResetPasswordResponse.builder()
                .newPassword(newPassword)
                .build();

    }

    @Override
    public void changePassword(@Valid ChangePasswordRequest request, HttpServletRequest httpRequest) throws BadRequestError {
        String email = (String) httpRequest.getAttribute("email");
        Optional<User> user = userRepository.findByEmail(email);

        boolean passwordMatches = passwordEncoder.matches(request.getNewPassword(), user.get().getPassword());

        if(passwordMatches){
            throw new BadRequestError("New password cannot be the same as old password");
        }

        user.get().setPassword(passwordEncoder.encode(request.getNewPassword()));

        userRepository.save(user.get());
    }

}
