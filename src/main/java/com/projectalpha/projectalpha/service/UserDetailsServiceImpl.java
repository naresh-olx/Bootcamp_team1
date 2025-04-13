package com.projectalpha.projectalpha.service;

import com.projectalpha.projectalpha.entity.UserEntity;
import com.projectalpha.projectalpha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByEmailId(username);

        if (userEntity != null) {
            return User.builder()
                    .username(userEntity.getEmailId())
                    .password(userEntity.getPassword())
                    .build();
        }
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
