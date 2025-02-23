package it.eni.extracrypto.model.enums;

public enum CryptoName {
    KASPA(20396),
    BITCOIN(1),
    SOLANA(5426),
    ETHEREUM(1027),
    DOGECOIN(74),
    SHIBAINU(5994);

    public final Integer id;

    CryptoName(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
