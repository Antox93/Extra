package it.eni.extracrypto.model.entity;

import it.eni.extracrypto.model.enums.CryptoName;
import it.eni.extracrypto.model.enums.Network;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name="crypto_wallet")
@Data
public class CryptoWallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private CryptoName cryptoName;

    @Column
    private BigDecimal amount;

    @Column
    private String walletAddress;

    @Column
    @Enumerated(EnumType.STRING)
    private Network network;
}
