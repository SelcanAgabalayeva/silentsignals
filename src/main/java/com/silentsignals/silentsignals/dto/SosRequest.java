package com.silentsignals.silentsignals.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SosRequest implements Serializable {
    private Long userId;
    private double latitude;
    private double longitude;
    private String email;
}
