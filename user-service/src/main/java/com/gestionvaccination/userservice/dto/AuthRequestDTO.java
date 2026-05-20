package com.gestionvaccination.userservice.dto;


import com.gestionvaccination.userservice.enumeration.LangueMaternelle;
import lombok.Data;

@Data
public class AuthRequestDTO {
    private String username;
    private String password;
    private String role;
    private LangueMaternelle langueMaternelle;

}
