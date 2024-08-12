package it.eni.extracrypto.controller;

import it.eni.extracrypto.model.dto.CreateUserDto;
import it.eni.extracrypto.model.entity.User;
import it.eni.extracrypto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void createUser (@RequestBody CreateUserDto request){
        userService.createUser(request);
    }

    @GetMapping
    public ResponseEntity<User> login(@RequestHeader("Authorization") String auth){
        User user= userService.login(auth);

        if (user!= null){
            return ResponseEntity.ok(user);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
