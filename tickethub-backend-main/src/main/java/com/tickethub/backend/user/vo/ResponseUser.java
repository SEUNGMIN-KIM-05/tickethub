package com.tickethub.backend.user.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tickethub.backend.user.dto.UserDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {

    private String username;
    private Long userId;
    private String role;

    // dto -> response
    public static ResponseUser fromDto(UserDto userDto) {
        return ResponseUser.builder()
                .username(userDto.getUsername())
                .userId(userDto.getUserId())
                .role(userDto.getRole())
                .build();
    }
}