package it.eni.extracrypto.service;

import it.eni.extracrypto.model.dto.CreateTransactionDto;
import it.eni.extracrypto.model.entity.CryptoWallet;
import it.eni.extracrypto.model.entity.Transaction;
import it.eni.extracrypto.repository.CryptoWalletRepository;
import it.eni.extracrypto.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final CryptoWalletRepository cryptoWalletRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, CryptoWalletRepository cryptoWalletRepository){

        this.transactionRepository = transactionRepository;
        this.cryptoWalletRepository = cryptoWalletRepository;
    }


    public List<Transaction> findTransactions(String walletAddress) {
        return transactionRepository.findByWalletAddressRecipient(walletAddress);

    }

   @Transactional
    public void createTransaction  (CreateTransactionDto dto){
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getAmount());
        transaction.setFee(dto.getNetwork().fee);
        transaction.setCryptoName(dto.getCryptoName());
        transaction.setNetwork(dto.getNetwork());
        transaction.setTTimestamp(LocalDateTime.now());
        transaction.setWalletAddressRecipient(dto.getWalletRecipient());
        transaction.setWalletAddressSender(dto.getWalletSender());
        transactionRepository.save(transaction);

        Optional<CryptoWallet> cryptoOpt=cryptoWalletRepository.findByWalletAddressAndNetworkAndCryptoName(dto.getWalletSender(),dto.getNetwork(),dto.getCryptoName());
        if(cryptoOpt.isPresent()){
            CryptoWallet crypto= cryptoOpt.get();
            BigDecimal result= crypto.getAmount().subtract(transaction.getAmount());
            crypto.setAmount(result);
            cryptoWalletRepository.save(crypto);

        }
       Optional<CryptoWallet> cryptoRecipientOpt=cryptoWalletRepository.findByWalletAddressAndNetworkAndCryptoName(dto.getWalletRecipient(),dto.getNetwork(),dto.getCryptoName());
       if(cryptoRecipientOpt.isPresent()){
           CryptoWallet crypto= cryptoRecipientOpt.get();
           BigDecimal result= crypto.getAmount().add(transaction.getAmount());
           crypto.setAmount(result);
           cryptoWalletRepository.save(crypto);
       }else{
           CryptoWallet cryptoWallet = new CryptoWallet();
           cryptoWallet.setCryptoName(dto.getCryptoName());
           cryptoWallet.setNetwork(dto.getNetwork());
           cryptoWallet.setWalletAddress(dto.getWalletRecipient());
           cryptoWallet.setAmount(dto.getAmount());
           cryptoWalletRepository.save(cryptoWallet);
       }

    }


}
