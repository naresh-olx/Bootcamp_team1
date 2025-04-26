package com.projectalpha.service;

import com.projectalpha.entity.UserEntity;
import com.projectalpha.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
