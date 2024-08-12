package it.eni.extracrypto.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="wallet")
@Data
public class Wallet{
    @Id
    private  String id;

    @Column
    private Long userId;

    @Column
    private  String recoveryKey;

    @Column
    private String password;
}
