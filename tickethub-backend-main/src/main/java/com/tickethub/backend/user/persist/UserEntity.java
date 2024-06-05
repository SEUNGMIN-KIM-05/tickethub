package com.tickethub.backend.user.persist;

import com.tickethub.backend.book.persist.BookEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name="username", nullable = false, length = 50, unique = true)
    private String username;

    @Column(name="encrypted_pwd", nullable = false)
    private String encryptedPwd;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "userEntity")
    private List<BookEntity> books = new ArrayList<>();
}
