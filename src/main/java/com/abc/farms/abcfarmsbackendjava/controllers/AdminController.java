package com.abc.farms.abcfarmsbackendjava.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abc.farms.abcfarmsbackendjava.services.httpServices.ApiResponse;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.ResponseUtils;
import com.abc.farms.abcfarmsbackendjava.services.httpServices.responseMappings.admin.AdminGetUsersResponse;
import com.abc.farms.abcfarmsbackendjava.services.interfaces.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    @Autowired
    private final AdminService adminService;

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    // TODO: Test
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse> getUsers() {
        logger.info(">>> GET /api/admin/users <<<");

        try {
            AdminGetUsersResponse adminGetUsersResponse = adminService.getUsers();

            ResponseEntity<ApiResponse> response = ResponseUtils.createApiResponse(HttpStatus.OK, adminGetUsersResponse,
                    "success");
            return response;

        } catch (Exception e) {
            throw new Error(e.getMessage(), e.getCause());
        }

    }

}
