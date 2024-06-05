package com.tickethub.backend.user.vo;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUser {

    @NotNull
    private String username;

    @NotNull
    private String password;

    private String role;
}
