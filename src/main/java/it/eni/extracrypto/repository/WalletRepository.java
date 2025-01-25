package it.eni.extracrypto.repository;

import it.eni.extracrypto.model.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,String> {
    Wallet findByUserId(Long userId);


}
