package com.example.authenticationspring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "user")
public class UserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "UserID", nullable = false)
    private Integer userId;
    @Basic
    @Column(name = "First_Name", nullable = true, length = 255)
    private String firstName;
    @Basic
    @Column(name = "Dob", nullable = true)
    private LocalDate dob;
    @Basic
    @Column(name = "Gender", nullable = true)

    private Boolean gender;
    @Basic
    @Column(name = "Phone", nullable = true, length = 45)
    private String phone;
    @Basic
    @Column(name = "Email", nullable = true, length = 45)
    private String email;
    @Basic
    @Column(name = "Password", nullable = true, length = 255)
    private String password;

    @Basic
    @Column(name = "Address", nullable = true, length = 255)
    private String address;

    @Basic
    @Column(name = "Role")
    private String role;

    @Basic
    @Column(name = "Created_Date", nullable = true)
    private Timestamp createdDate;
    @Basic
    @Column(name = "Last_Modified_Date", nullable = true)
    private Timestamp lastModifiedDate;
    @Basic
    @Column(name = "Status", nullable = true)
    private String status;
    @Basic
    @Column(name = "Last_Name", nullable = true, length = 255)
    private String lastName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return userId == that.userId && Objects.equals(firstName, that.firstName) && Objects.equals(dob, that.dob) && Objects.equals(gender, that.gender) && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) && Objects.equals(password, that.password)  && Objects.equals(role, that.role) && Objects.equals(createdDate, that.createdDate) && Objects.equals(lastModifiedDate, that.lastModifiedDate) && Objects.equals(status, that.status) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, firstName, dob, gender, phone, email, password, role, createdDate, lastModifiedDate, status,  lastName);
    }

}
