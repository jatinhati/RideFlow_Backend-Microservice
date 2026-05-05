package com.example.rideflowprojectauthservice.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDto {
    private String email;
    private String password;
}


/**
 * {email: '', password: ''}
 */