package it.eni.extracrypto.model.dto;

import lombok.Data;

@Data
public class CreateUserDto {
    private String username;
    private String password;

}
