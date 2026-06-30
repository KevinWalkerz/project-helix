package com.helix.zibrary.enumeration.security;

public enum AccessType {
    INSERT("Insert", "Tambah Data"),
    UPDATE("Update", "Ubah Data"),
    DELETE("Delete", "Hapus Data"),
    VIEW("View", "Lihat Data");

    private final String eng;
    private final String idn;

    AccessType(String eng, String idn){
        this.eng = eng;
        this.idn = idn;
    }

    public String getEng() {
        return eng;
    }
    public String getIdn() {
        return idn;
    }
}
