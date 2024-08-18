package it.eni.extracrypto.service;

import it.eni.extracrypto.exception.BusinessException;
import it.eni.extracrypto.model.dto.CreateTransactionDto;
import it.eni.extracrypto.model.entity.CryptoWallet;
import it.eni.extracrypto.model.entity.Transaction;
import it.eni.extracrypto.model.enums.ErrorEnum;
import it.eni.extracrypto.repository.CryptoWalletRepository;
import it.eni.extracrypto.repository.TransactionRepository;
import it.eni.extracrypto.util.Utils;
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
        return transactionRepository.findByWalletAddressStarter(walletAddress);

    }

   @Transactional
    public void createTransaction  (CreateTransactionDto dto){
        Transaction transactionRecipient = createEntity(dto);
        transactionRecipient.setFee(BigDecimal.ZERO);
        transactionRecipient.setWalletAddressStarter(dto.getWalletRecipient());
        transactionRepository.save(transactionRecipient);

       Transaction transactionSender =createEntity(dto);
       transactionSender.setFee(dto.getNetwork().fee);
       transactionSender.setAmount(dto.getAmount().negate());
       transactionSender.setWalletAddressStarter(dto.getWalletSender());
       transactionRepository.save(transactionSender);

        Optional<CryptoWallet> cryptoOpt=cryptoWalletRepository.findByWalletAddressAndNetworkAndCryptoName(dto.getWalletSender(),dto.getNetwork(),dto.getCryptoName());
        if(cryptoOpt.isPresent()){
            CryptoWallet crypto= cryptoOpt.get();
            BigDecimal result= crypto.getAmount().subtract(transactionSender.getAmount().negate()).subtract(transactionSender.getFee());

            if(result.compareTo(BigDecimal.ZERO) < 0){
                throw new BusinessException(ErrorEnum.AMOUNT_EXCEEDED);

            }

            crypto.setAmount(result);
            cryptoWalletRepository.save(crypto);

        }
       Optional<CryptoWallet> cryptoRecipientOpt=cryptoWalletRepository.findByWalletAddressAndNetworkAndCryptoName(dto.getWalletRecipient(),dto.getNetwork(),dto.getCryptoName());
       if(cryptoRecipientOpt.isPresent()){
           CryptoWallet crypto= cryptoRecipientOpt.get();
           BigDecimal result= crypto.getAmount().add(transactionRecipient.getAmount());
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
    private static Transaction createEntity(CreateTransactionDto dto){
        Transaction transaction = new Transaction();
        transaction.setId(Utils.getLongUuid());
        transaction.setAmount(dto.getAmount());
        transaction.setCryptoName(dto.getCryptoName());
        transaction.setNetwork(dto.getNetwork());
        transaction.setTTimestamp(LocalDateTime.now());
        transaction.setWalletAddressRecipient(dto.getWalletRecipient());
        transaction.setWalletAddressSender(dto.getWalletSender());
        return transaction;
    }


}
