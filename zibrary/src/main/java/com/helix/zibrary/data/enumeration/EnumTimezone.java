package com.helix.zibrary.data.enumeration;

public enum EnumTimezone {

    WIB("WIB","Asia/Jakarta", "Waktu Indonesia Barat", "Western Indonesian Time"),
    WITA("WITA","Asia/Makassar", "Waktu Indonesia Tengah", "Central Indonesian Time"),
    WIT("WIT","Asia/Jayapura", "Waktu Indonesia Timur", "East Indonesian Time");

    private final String shortTimezone;
    private final String zoneId;
    private final String longTimezone;
    private final String longTimezoneEN;


    EnumTimezone(String shortTimezone, String zoneId, String longTimezone, String longTimezoneEN) {
        this.shortTimezone = shortTimezone;
        this.zoneId = zoneId;
        this.longTimezone = longTimezone;
        this.longTimezoneEN = longTimezoneEN;
    }

    public String getShortTimezone() {
        return shortTimezone;
    }

    public String getZoneId() {
        return zoneId;
    }

    public String getLongTimezone() { return longTimezone;}

    public String getLongTimezoneEN(){ return longTimezoneEN;
    }

    public static EnumTimezone getFromShortTimezone(String value) {
        for (EnumTimezone status : values()) {
            if (status.getShortTimezone().equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
}
