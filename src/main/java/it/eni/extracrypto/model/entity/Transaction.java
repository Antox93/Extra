package it.eni.extracrypto.model.entity;

import it.eni.extracrypto.model.enums.CryptoName;
import it.eni.extracrypto.model.enums.Network;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="transaction")
@Data
public class Transaction {
    @Id
    private String id;

    @Column
    private String walletAddressRecipient;

    @Column
    private String walletAddressSender;

    @Column
    private String walletAddressStarter;

    @Column
    @Enumerated(EnumType.STRING)
    private Network network;

    @Column
    private LocalDateTime tTimestamp;

    @Column
    @Enumerated(EnumType.STRING)
    private CryptoName cryptoName;

    @Column
    private BigDecimal amount;

    @Column
    private BigDecimal fee;


}
