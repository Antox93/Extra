package it.eni.extracrypto.service;

import it.eni.extracrypto.exception.BusinessException;
import it.eni.extracrypto.model.dto.CreateUserDto;
import it.eni.extracrypto.model.dto.UserConfigDto;
import it.eni.extracrypto.model.dto.UserDto;
import it.eni.extracrypto.model.entity.User;
import it.eni.extracrypto.model.entity.UserConfig;
import it.eni.extracrypto.model.entity.Wallet;
import it.eni.extracrypto.model.enums.CryptoName;
import it.eni.extracrypto.model.enums.ErrorEnum;
import it.eni.extracrypto.model.enums.Network;
import it.eni.extracrypto.repository.UserConfigRepository;
import it.eni.extracrypto.repository.UserRepository;
import it.eni.extracrypto.repository.WalletRepository;
import it.eni.extracrypto.util.Utils;
import jakarta.transaction.Transactional;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;


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
        userConfig.setFavouriteNetwork(Network.ETHEREUM);
        userConfigRepository.save(userConfig);

        Wallet wallet = new Wallet();
        wallet.setUserId(userSaved.getId());
        wallet.setRecoveryKey(Utils.getLongUuid());
        wallet.setId(Utils.getLongUuid());
        walletRepository.save(wallet);
    }

    public UserDto login(String auth){
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
                Wallet wallet = walletRepository.findByUserId(user.getId());
                UserDto userDto = new UserDto();
                userDto.setUsername(user.getUsername());
                userDto.setId(user.getId());
                userDto.setAddressId(wallet.getId());


                return userDto;

            }
        }
        return null;
    }


    private String generatePassword(String password){
        String salt = Utils.generateSalt();
        byte[]  hashedPassword = DigestUtils.sha256(salt+password);
       return salt+ ":"+ Base64.getEncoder().encodeToString(hashedPassword);

    }

    public UserConfigDto findUserConfig(Long userId){
       UserConfig userConfig = userConfigRepository.findByUserId(userId);
       UserConfigDto dto = new UserConfigDto();
       dto.setFavouriteNetwork(userConfig.getFavouriteNetwork());
       dto.setFavouriteCrypto(createFavouriteCrypto(userConfig.getFavouriteCrypto()));
       return dto;
    }

    private List<CryptoName> createFavouriteCrypto(String favouriteCrypto) {
        List<CryptoName> cryptoList = new ArrayList<>();
        if(favouriteCrypto!= null) {
            String[] cryptoArray = favouriteCrypto.split(";");
            for (String s : cryptoArray) {
                cryptoList.add(CryptoName.valueOf(s));
            }
        }
        return cryptoList;
    }

    public void addFavouriteCrypto (Long userId, Integer cryptoId){
      UserConfig find = userConfigRepository.findByUserId(userId);
      CryptoName crypto = CryptoName.getName(cryptoId);
      String favouriteCrypto= crypto.toString()+";";
      if(find.getFavouriteCrypto() == null){
          find.setFavouriteCrypto(favouriteCrypto);

      }else if(!find.getFavouriteCrypto().contains(favouriteCrypto)){
          find.setFavouriteCrypto(find.getFavouriteCrypto()+favouriteCrypto);
      }
      userConfigRepository.save(find);
    }

    public List<Integer> getFavouriteCrypto(Long userId){
        UserConfig find = userConfigRepository.findByUserId(userId);

        if (find != null && find.getFavouriteCrypto() != null) {
            return Arrays.stream(find.getFavouriteCrypto().split(";"))
                    .map(CryptoName::valueOf)
                    .map(CryptoName::getId)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public void updateFavouriteNetwork (Long userId, Network network){
        UserConfig find = userConfigRepository.findByUserId(userId);
        find.setFavouriteNetwork(network);


        userConfigRepository.save(find);
    }

    public void deleteFavouriteCrypto (Long userId, Integer cryptoId){
        UserConfig find = userConfigRepository.findByUserId(userId);
        CryptoName crypto = CryptoName.getName(cryptoId);
        if(find.getFavouriteCrypto() != null && find.getFavouriteCrypto().contains(crypto.toString())){
            String replaced = find.getFavouriteCrypto().replace(crypto+";","");
            find.setFavouriteCrypto(replaced);
            userConfigRepository.save(find);
        }
    }
    public void changePassword(String auth,Long userId){
        byte[] decodedAuth = Base64.getDecoder().decode(auth.replaceAll("Basic ", ""));
        String newPassword= new String(decodedAuth, StandardCharsets.UTF_8).split(":")[0];
        String oldPassword =  new String(decodedAuth, StandardCharsets.UTF_8).split(":")[1];

        Optional <User> userOpt = userRepository.findById(userId);
        if(userOpt.isPresent()){
            var user = userOpt.get();
            String salt = user.getPassword().split(":")[0];
            String hashedPasswordUser = user.getPassword().split(":")[1];

            byte[] hashedPasswordBytes = DigestUtils.sha256(salt + oldPassword);
            String hashedPassword = Base64.getEncoder().encodeToString(hashedPasswordBytes);
            if (hashedPassword.equals(hashedPasswordUser)){
                user.setPassword(generatePassword(newPassword));
                userRepository.save(user);

            }
        }


    }
    public void changeUsername(String auth,Long userId){
        byte[] decodedAuth = Base64.getDecoder().decode(auth.replaceAll("Basic ", ""));
        String newUsername= new String(decodedAuth, StandardCharsets.UTF_8).split(":")[0];
        String oldPassword =  new String(decodedAuth, StandardCharsets.UTF_8).split(":")[1];

        Optional <User> userOpt = userRepository.findById(userId);
        if(userOpt.isPresent()){
            var user = userOpt.get();
            String salt = user.getPassword().split(":")[0];
            String hashedPasswordUser = user.getPassword().split(":")[1];

            byte[] hashedPasswordBytes = DigestUtils.sha256(salt + oldPassword);
            String hashedPassword = Base64.getEncoder().encodeToString(hashedPasswordBytes);
            if (hashedPassword.equals(hashedPasswordUser)){
                user.setUsername(newUsername);
                userRepository.save(user);

            }
        }


    }

}
