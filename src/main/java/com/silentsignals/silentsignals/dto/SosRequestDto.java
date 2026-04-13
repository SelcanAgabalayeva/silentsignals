package com.silentsignals.silentsignals.dto;

import lombok.Data;

import java.io.Serializable;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;

@Data
public class SosRequestDto implements Serializable {


    @NotNull(message = "Latitude boş ola bilməz")
    @DecimalMin(value = "-90.0", message = "Latitude düzgün deyil")
    @DecimalMax(value = "90.0", message = "Latitude düzgün deyil")
    private Double latitude;

    @NotNull(message = "Longitude boş ola bilməz")
    @DecimalMin(value = "-180.0", message = "Longitude düzgün deyil")
    @DecimalMax(value = "180.0", message = "Longitude düzgün deyil")
    private Double longitude;

    @NotBlank(message = "Email boş ola bilməz")
    @Email(message = "Email düzgün formatda deyil")
    private String email;
}