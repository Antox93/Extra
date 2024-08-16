package it.eni.extracrypto.controller;

import it.eni.extracrypto.model.dto.CreateTransactionDto;
import it.eni.extracrypto.model.entity.Transaction;
import it.eni.extracrypto.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> searchTransactions(@RequestParam String walletAddress){
        List<Transaction> transactions = transactionService.findTransactions(walletAddress);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping
    public void createTransaction (@RequestBody CreateTransactionDto dto){
        transactionService.createTransaction(dto);
    }

}
