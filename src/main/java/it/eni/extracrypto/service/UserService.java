package it.eni.extracrypto.service;

import it.eni.extracrypto.exception.BusinessException;
import it.eni.extracrypto.model.dto.CreateUserDto;
import it.eni.extracrypto.model.entity.User;
import it.eni.extracrypto.model.entity.UserConfig;
import it.eni.extracrypto.model.entity.Wallet;
import it.eni.extracrypto.model.enums.ErrorEnum;
import it.eni.extracrypto.repository.UserConfigRepository;
import it.eni.extracrypto.repository.UserRepository;
import it.eni.extracrypto.repository.WalletRepository;
import it.eni.extracrypto.util.Utils;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;


@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserConfigRepository userConfigRepository;
    private final WalletRepository walletRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserConfigRepository userConfigRepository,
                       WalletRepository walletRepository) {

        this.userRepository = userRepository;
        this.userConfigRepository = userConfigRepository;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public void createUser(CreateUserDto createUserDto){
        User user=new User();
        user.setUsername(createUserDto.getUsername());
        user.setPassword(generatePassword(createUserDto.getPassword()));

        User userFound = userRepository.findByUsername(createUserDto.getUsername());


        if(userFound != null){
            throw new BusinessException(ErrorEnum.NAME_ALREADY_USED);
        }

        User userSaved= userRepository.save(user);
        UserConfig userConfig = new UserConfig();
        userConfig.setUserId(userSaved.getId());
        userConfigRepository.save(userConfig);

        Wallet wallet = new Wallet();
        wallet.setUserId(userSaved.getId());
        wallet.setRecoveryKey(getUuid());
        wallet.setId(getUuid());
        walletRepository.save(wallet);
    }

    private static String getUuid(){
        return UUID.randomUUID().toString().replace("-","")+UUID.randomUUID().toString().replace("-","");

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
