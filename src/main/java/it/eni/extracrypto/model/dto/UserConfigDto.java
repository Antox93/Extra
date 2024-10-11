package it.eni.extracrypto.model.dto;

import it.eni.extracrypto.model.enums.CryptoName;
import it.eni.extracrypto.model.enums.Network;
import lombok.Data;

import java.util.List;
@Data

public class UserConfigDto {
    private List<CryptoName> favouriteCrypto;
    private Network favouriteNetwork;
}
