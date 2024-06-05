package com.tickethub.backend.user.vo;


import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestLogin {

    @NotNull
    private String username;

    @NotNull
    private String password;

}
