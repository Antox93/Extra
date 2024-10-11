package it.eni.extracrypto.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

}
