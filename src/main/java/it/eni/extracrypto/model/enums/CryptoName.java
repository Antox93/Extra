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

    public static CryptoName getName(Integer id) {
        for (CryptoName cryptoName : CryptoName.values()) {
            if (cryptoName.getId().equals(id)) {
                return cryptoName;
            }
        }
        return CryptoName.BITCOIN;
    }
}
