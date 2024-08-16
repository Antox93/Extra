package it.eni.extracrypto.model.enums;

import java.math.BigDecimal;

public enum Network {
    KASPA(BigDecimal.valueOf(0.001)),
    BITCOIN(BigDecimal.valueOf(100)),
    SOLANA(BigDecimal.valueOf(0.01)),
    ETHEREUM(BigDecimal.valueOf(10)),
    DOGECOIN(BigDecimal.valueOf(0.1));

    public final BigDecimal fee;

    Network(BigDecimal fee) {
        this.fee = fee;
    }
}


