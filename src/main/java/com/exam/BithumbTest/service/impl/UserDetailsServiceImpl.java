package com.exam.BithumbTest.service.impl;

import com.exam.BithumbTest.domain.entity.UserEntity;
import com.exam.BithumbTest.domain.repository.UserRepository;
import com.exam.BithumbTest.enums.role.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        UserEntity userDto = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));

        if (email.equals("admin@bithumb.com")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        }

        return new User(userDto.getEmail(), userDto.getPassword(), grantedAuthorities);
    }
}
