package com.abc.farms.abcfarmsbackendjava.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.farms.abcfarmsbackendjava.repositories.UserRepository;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.admin.AdminGetUsersResponse;
import com.abc.farms.abcfarmsbackendjava.services.interfaces.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public AdminGetUsersResponse getUsers() {
        int totalCount = userRepository.findAll().size();

        AdminGetUsersResponse adminGetUsersResponse = AdminGetUsersResponse.builder()
                .totalCount(totalCount)
                .build();

        return adminGetUsersResponse;

    }

}
