package it.eni.extracrypto.model.entity;

import it.eni.extracrypto.model.enums.Network;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="user_config")
@Data
public class UserConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long userId;

    @Column
    private String favouriteCrypto;

    @Column
    @Enumerated(EnumType.STRING)
    private Network favouriteNetwork;

}
