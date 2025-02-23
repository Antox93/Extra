package it.eni.extracrypto.controller;

import it.eni.extracrypto.model.dto.CreateUserDto;
import it.eni.extracrypto.model.dto.UserConfigDto;
import it.eni.extracrypto.model.dto.UserDto;
import it.eni.extracrypto.model.enums.CryptoName;
import it.eni.extracrypto.model.enums.Network;
import it.eni.extracrypto.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("user")

public class UserController {
    private final UserService userService;



    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public void createUser (@Valid @RequestBody CreateUserDto request){
        userService.createUser(request);
    }


    @GetMapping("/login")
    public ResponseEntity<UserDto> login(@RequestHeader("Authorization") String auth){
        UserDto user= userService.login(auth);

        if (user!= null){
            return ResponseEntity.ok(user);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/config/{userId}")
    public ResponseEntity<UserConfigDto> getConfig(@PathVariable Long userId){
        UserConfigDto userConfig = userService.findUserConfig(userId);
        return ResponseEntity.ok(userConfig);

    }



    @PatchMapping("/config/network/{userId}")
    public void updateFavouriteNetwork (@PathVariable Long userId, @RequestParam Network network){
        userService.updateFavouriteNetwork(userId,network);
    }

    @PostMapping("/{userId}/favourite-crypto")
    public void addFavouriteCrypto (@PathVariable Long userId, @RequestParam CryptoName crypto){
        userService.addFavouriteCrypto(userId,crypto);
    }

    @GetMapping("/{userId}/favourite-crypto")
    public ResponseEntity<List<Integer>> getFavouriteCrypto(@PathVariable Long userId){
        List<Integer> favouriteCrypto = userService.getFavouriteCrypto(userId);
        return ResponseEntity.ok(favouriteCrypto);
    }

    @DeleteMapping ("/{userId}/favourite-crypto")
    public void deleteFavouriteCrypto(@PathVariable Long userId, @RequestParam CryptoName crypto){
        userService.deleteFavouriteCrypto(userId,crypto);
    }
}
