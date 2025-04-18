package com.projectalpha.service;

import com.projectalpha.dto.UserLoginDTO;
import com.projectalpha.dto.UserRequestDTO;
import com.projectalpha.dto.UserResponseDTO;

import com.projectalpha.entity.UserEntity;
import com.projectalpha.mapper.UserMapper;
import com.projectalpha.repository.UserRepository;
import com.projectalpha.utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class UserServices {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public UserResponseDTO registerUser(@Valid UserRequestDTO userDTO) {

        if (userRepository.existsByEmailId(userDTO.getEmailId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
        }

        UserEntity userEntity = UserMapper.toEntity(userDTO);
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        UserEntity saved = userRepository.save(userEntity);
        return UserMapper.toResponse(saved);
    }

    public String loginUser(@Valid UserLoginDTO user) {

        if (!userRepository.existsByEmailId(user.getEmailId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User doesn't exist with EmailId: " + user.getEmailId()
            );
        }

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmailId(), user.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Incorrect password");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmailId());

        return jwtUtil.generateToken(userDetails.getUsername());
    }
}
