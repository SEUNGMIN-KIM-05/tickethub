package com.tickethub.backend.user.service;

import com.tickethub.backend.user.dto.CustomUserDetails;
import com.tickethub.backend.user.dto.UserDto;
import com.tickethub.backend.user.persist.UserEntity;
import com.tickethub.backend.user.persist.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다"));

        return new CustomUserDetails(userEntity);
    }

    /**
     * 회원가입
     */
    @Transactional
    @Override
    public UserDto createUser(String username, String pwd, String role) {

        boolean exists = userRepository.existsByUsername(username);
        if(exists) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .encryptedPwd(passwordEncoder.encode(pwd)) // password encode
                .role(role)
                .build();

        return UserDto.from(userRepository.save(userEntity));
    }

    // 유저 조회(by userId)
    @Override
    public UserDto getUserByUserId(Long userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        return UserDto.from(userEntity);
    }


}
