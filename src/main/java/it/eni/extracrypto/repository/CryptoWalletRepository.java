package it.eni.extracrypto.repository;


import it.eni.extracrypto.model.entity.CryptoWallet;
import it.eni.extracrypto.model.enums.CryptoName;
import it.eni.extracrypto.model.enums.Network;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CryptoWalletRepository extends JpaRepository<CryptoWallet,Long> {
    Optional<CryptoWallet> findByWalletAddressAndNetworkAndCryptoName(String walletAddress, Network network, CryptoName cryptoName);

    List<CryptoWallet> findByWalletAddressAndNetwork(String walletAddress,Network network);

    List<CryptoWallet> findByWalletAddress(String walletAddress);
}




