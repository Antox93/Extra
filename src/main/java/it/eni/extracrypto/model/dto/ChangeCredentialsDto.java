package it.eni.extracrypto.model.dto;

import lombok.Data;

@Data
public class ChangeCredentialsDto {
    private String username;
    private String password;
    private String oldPassword;


}
