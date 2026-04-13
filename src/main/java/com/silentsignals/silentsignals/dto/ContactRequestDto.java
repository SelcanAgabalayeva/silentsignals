package com.silentsignals.silentsignals.dto;

import lombok.Data;

import jakarta.validation.constraints.*;


@Data
public class ContactRequestDto {

    @NotNull(message = "UserId boş ola bilməz")
    private Long userId;

    @NotBlank(message = "Ad boş ola bilməz")
    @Size(min = 2, max = 50, message = "Ad 2-50 simvol olmalıdır")
    private String name;

    @NotBlank(message = "Email boş ola bilməz")
    @Email(message = "Email düzgün formatda deyil")
    private String email;

    @NotBlank(message = "Telefon boş ola bilməz")
    @Pattern(regexp = "\\+?[0-9]{10,14}", message = "Telefon formatı düzgün deyil")
    private String phone;
}
