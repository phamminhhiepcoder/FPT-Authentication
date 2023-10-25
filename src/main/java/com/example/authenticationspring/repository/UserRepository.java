package com.example.authenticationspring.repository;
import com.example.authenticationspring.entity.UserEntity;
import com.example.authenticationspring.model.dto.UserDto;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@SpringBootApplication

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public UserDto save(UserDto userDto);
    Optional<UserEntity> findByEmail(String email);
    @Modifying
    @Query("UPDATE UserEntity as u set u.password =?1 where u.email=?2")
    int updatePassword(String password, String email);
}
