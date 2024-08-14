package it.eni.extracrypto.service;

import it.eni.extracrypto.model.dto.TransactionResponse;
import it.eni.extracrypto.model.entity.Transaction;
import it.eni.extracrypto.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository){

        this.transactionRepository = transactionRepository;
    }


    public List<TransactionResponse> findTransactions(String walletAddress) {
        List<Transaction> wall = transactionRepository.findByWalletAddressRecipient(walletAddress);
        List<TransactionResponse> response = new ArrayList<>();

        for (Transaction transaction : wall) {
            TransactionResponse transactionResponse = TransactionResponse.builder()
                    .amount(transaction.getAmount())
                    .network(transaction.getNetwork())
                    .walletAddressRecipient(transaction.getWalletAddressRecipient())
                    .walletAddressSender(transaction.getWalletAddressSender())
                    .build();
            response.add(transactionResponse);

        }
        return response;
    }


}
