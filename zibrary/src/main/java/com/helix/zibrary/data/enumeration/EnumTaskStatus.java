package com.helix.zibrary.data.enumeration;

public enum EnumTaskStatus {

    SUCCESS("Success"),
    EXCEPTION("Exception"),
    DATA_VALIDATION_ERROR("Data Validation Error");

    private final String caption;

    EnumTaskStatus(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }

    public static EnumTaskStatus getFromCaption(String caption) {
        for (EnumTaskStatus status : values()) {
            if (status.getCaption().equalsIgnoreCase(caption)) {
                return status;
            }
        }
        return null;
    }

}
