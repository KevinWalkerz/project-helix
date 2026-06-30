package com.helix.zibrary.enumeration.general;

public enum ActiveStatus {
    ALL("All", "Semua", null),
    ACTIVE("Active", "Aktif", true),
    INACTIVE("Inactive", "Tidak Aktif", false);

    private final String eng;
    private final String idn;
    private final Boolean value;

    ActiveStatus(String eng, String idn, Boolean value){
        this.eng = eng;
        this.idn = idn;
        this.value = value;
    }

    public String getEng() {
        return eng;
    }
    public String getIdn() {
        return idn;
    }
    public Boolean getValue() {
        return value;
    }
}
