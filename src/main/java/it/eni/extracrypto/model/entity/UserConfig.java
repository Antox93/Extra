package it.eni.extracrypto.model.entity;

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

}
