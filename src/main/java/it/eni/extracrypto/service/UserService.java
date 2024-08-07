package it.eni.extracrypto.service;

import it.eni.extracrypto.model.dto.CreateUserDto;
import it.eni.extracrypto.model.entity.User;
import it.eni.extracrypto.repository.UserRepository;
import it.eni.extracrypto.util.Utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(CreateUserDto createUserDto){
        User user=new User();
        user.setUsername(createUserDto.getUsername());
        user.setPassword(generatePassword(createUserDto.getPassword()));

        userRepository.save(user);

    }

    public User login(String auth){
        byte[] decodedAuth = Base64.getDecoder().decode(auth.replaceAll("Basic ", ""));
        String username = new String(decodedAuth, StandardCharsets.UTF_8).split(":")[0];
        String password =  new String(decodedAuth, StandardCharsets.UTF_8).split(":")[1];

        User user = userRepository.findByUsername(username);

        if(user != null){
            String salt = user.getPassword().split(":")[0];
            String hashedPasswordUser = user.getPassword().split(":")[1];

            byte[] hashedPasswordBytes = DigestUtils.sha256(salt + password);
            String hashedPassword = Base64.getEncoder().encodeToString(hashedPasswordBytes);
            if (hashedPassword.equals(hashedPasswordUser)){
                return user;
            }
        }
        return null;
    }


    private String generatePassword(String password){
        String salt = Utils.generateSalt();
        byte[]  hashedPassword = DigestUtils.sha256(salt+password);
       return salt+ ":"+ Base64.getEncoder().encodeToString(hashedPassword);

    }

}
