package io.ovida.test.controller;

import io.ovida.test.dto.UserDto;
import io.ovida.test.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    IUserService userService;

    public UserController() {
        super();
    }

    @GetMapping("/me")
    public UserDto getUser(@AuthenticationPrincipal UserDetails userDetails) {
        return userService.getMe(userDetails);
    }

    @GetMapping("/users")
    public Page<UserDto> listUsers(final Pageable pageable) {
        return userService.listUsers(pageable);
    }
}
