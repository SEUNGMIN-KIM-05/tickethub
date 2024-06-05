package com.tickethub.backend.user.dto;

import com.tickethub.backend.user.persist.UserEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long userId;
    private String username;
    private String email;
    private String role;

    // entity -> dto
    public static UserDto from(UserEntity user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }
}
