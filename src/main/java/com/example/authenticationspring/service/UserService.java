package com.example.authenticationspring.service;

import com.example.authenticationspring.entity.UserEntity;
import com.example.authenticationspring.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;


@Service
public interface UserService extends UserDetailsService {
    void saveUser(UserEntity user);

    Optional<UserEntity> findByEmail(String email);
    UserDto findByMail(String email);

    void registerUser(UserDto userDto);

    UserDto findById(int id);

    void updateProfile(UserDto userDto);

    UserEntity save(UserEntity user);

    int recoverPassword(String password, String email);
}

