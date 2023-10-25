package com.example.authenticationspring.service.impl;

import com.example.authenticationspring.config.SecurityUser;
import com.example.authenticationspring.entity.UserEntity;
import com.example.authenticationspring.model.dto.UserDto;
import com.example.authenticationspring.repository.UserRepository;
import com.example.authenticationspring.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //    chưa xử lý mã hóa code
    @Override
    public void saveUser(UserEntity user) {
        userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDto findByMail(String email) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        UserEntity user = optionalUser.get();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        if(user.getGender()) {
            userDto.setGender("Nam");
        }
        else {
            userDto.setGender("Nữ");
        }
        userDto.setDob(user.getDob().toString());
        System.out.println(userDto);
        return userDto;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userByUsername = userRepository.findByEmail(username);
        if (!userByUsername.isPresent()) {
            System.out.println("Could not find user with that username: {}");
            throw new UsernameNotFoundException("Invalid credentials!");
        }
        UserEntity user = userByUsername.get();
        if (user == null || !user.getEmail().equals(username)) {
            System.out.println("Could not find user with that username: {}");
            throw new UsernameNotFoundException("Invalid credentials!");        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        return new SecurityUser(user.getEmail(), user.getPassword(), true, true, true, true, grantedAuthorities,
                user.getFirstName(), user.getLastName(), user.getEmail());
    }
    @Override
    public void updateProfile(UserDto userDto) {
        UserEntity user = userRepository.findByEmail(userDto.getEmail()).get();

        UserEntity saveUser = new UserEntity();
//        Handle gender
        if (Objects.equals(userDto.getGender(), "Nam")) {
            saveUser.setGender(true);
        } else {
            saveUser.setGender(false);
        }
        if(Objects.equals(userDto.getPassword(), "")) {
            saveUser.setPassword(user.getPassword());
        }
        else {
            saveUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
//            Begin Mapping
//            UserDto:
        saveUser.setDob(LocalDate.parse(userDto.getDob()));
        saveUser.setFirstName(userDto.getFirstName());
        saveUser.setLastName(userDto.getLastName());
        saveUser.setLastModifiedDate(Timestamp.from(Instant.now()));
        saveUser.setPhone(userDto.getPhone());
        saveUser.setAddress(userDto.getAddress());
//        User:
        saveUser.setUserId(user.getUserId());
        saveUser.setEmail(user.getEmail());
        saveUser.setUserId(user.getUserId());
        saveUser.setCreatedDate(user.getCreatedDate());
        saveUser.setRole(user.getRole());
        saveUser.setStatus(user.getStatus());
        userRepository.save(saveUser);
    }
    @Override
    public int recoverPassword(String password, String email) {
        return userRepository.updatePassword(password,email);
    }
}
