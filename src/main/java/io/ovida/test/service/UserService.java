package io.ovida.test.service;

import io.ovida.test.dto.UserDto;
import io.ovida.test.persistence.dao.UserRepository;
import io.ovida.test.persistence.model.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto getMe(UserDetails userDetails) {
        return buildUserDto(userRepository.findByEmail(userDetails.getUsername()));
    }

    @Override
    @PreAuthorize("hasAuthority('LIST_USERS')")
    public Page<UserDto> listUsers(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::buildUserDto);
    }

    private UserDto buildUserDto(final User user) {
        return UserDto.newBuilder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }
}
