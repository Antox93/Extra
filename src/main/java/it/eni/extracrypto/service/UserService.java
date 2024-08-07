package it.eni.extracrypto.service;

import it.eni.extracrypto.model.dto.CreateUserDto;
import it.eni.extracrypto.model.entity.User;
import it.eni.extracrypto.repository.UserRepository;
import it.eni.extracrypto.util.Utils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private String generatePassword(String password){
        String salt = Utils.generateSalt();
        byte[]  hashedPassword = DigestUtils.sha256(salt+password);
       return salt+ ":"+ Base64.getEncoder().encodeToString(hashedPassword);

    }
}
