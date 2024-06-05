package com.tickethub.backend.user.service;

import com.tickethub.backend.user.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface UserService extends UserDetailsService{

    UserDto createUser(String username, String password, String role);
    //UserDto loginUser(String email, String password);

    UserDto getUserByUserId(Long userId);
    //List<UserDto> getUserByAll();
    //UserDto getUserDetailsByEmail(String email);

}
