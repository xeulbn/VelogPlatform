package org.example.velogplatform.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDto {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
