package it.eni.extracrypto.repository;

import it.eni.extracrypto.model.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
public interface TransactionRepository extends JpaRepository<Transaction,String> {
    @Query("SELECT t FROM Transaction t WHERE t.walletAddressStarter= :walletAddressStarter ORDER BY t.tTimestamp DESC")
    List<Transaction> findByWalletAddressStarter(String walletAddressStarter);
}
