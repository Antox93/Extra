package it.eni.extracrypto.repository;


import it.eni.extracrypto.model.entity.UserConfig;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserConfigRepository extends JpaRepository<UserConfig,Long> {
    UserConfig findByUserId(Long userId);


}
