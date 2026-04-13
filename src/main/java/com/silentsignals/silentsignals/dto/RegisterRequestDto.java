package com.silentsignals.silentsignals.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {
    @NotBlank(message = "Username boş ola bilməz")
    @Size(min = 3, max = 20, message = "Username 3-20 simvol olmalıdır")
    private String username;

    @NotBlank(message = "Password boş ola bilməz")
    @Size(min = 6, message = "Password ən az 6 simvol olmalıdır")
    private String password;
}
