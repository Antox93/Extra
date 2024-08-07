package it.eni.extracrypto.controller;

import it.eni.extracrypto.model.dto.CreateUserDto;
import it.eni.extracrypto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("user")
public class UserController {
    @Autowired
    private UserService userService;


    @PostMapping
    public void createUser (@RequestBody CreateUserDto request){
        userService.createUser(request);
    }
}
