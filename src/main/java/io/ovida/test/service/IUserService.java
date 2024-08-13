package io.ovida.test.service;

import io.ovida.test.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {

    UserDto getMe(UserDetails userDetails);

    Page<UserDto> listUsers(Pageable pageable);
}
