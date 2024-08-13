package io.ovida.test.service;

import io.ovida.test.persistence.dao.UserRepository;
import io.ovida.test.persistence.model.Permission;
import io.ovida.test.persistence.model.Role;
import io.ovida.test.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static io.ovida.test.constant.AppConstants.INVALID_USERNAME_PASSWORD_MESSAGE;

@Service("userDetailsService")
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public MyUserDetailsService() {
        super();
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        try {
            final User user = userRepository.findByEmail(email);
            if (user == null) {
                throw new UsernameNotFoundException(INVALID_USERNAME_PASSWORD_MESSAGE);
            }

            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true, true, true, true, getAuthorities(user.getRole()));
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final Role role) {
        return getGrantedAuthorities(role.getPermissions().stream().toList());
    }

    private List<GrantedAuthority> getGrantedAuthorities(final List<Permission> permissions) {
        return permissions.stream().map(p -> (GrantedAuthority) new SimpleGrantedAuthority(p.getName())).toList();
    }
}
