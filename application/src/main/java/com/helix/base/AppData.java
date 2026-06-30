package com.helix.base;

import com.vaadin.flow.server.VaadinSession;

import java.time.LocalDate;

public class AppData {

    LocalDate firstPeriod;
    LocalDate lastPeriod;


    public static AppData getInstance(){
        return VaadinSession.getCurrent().getAttribute(AppData.class);
    }

    public LocalDate getFirstPeriod() {
        return firstPeriod;
    }
    public void setFirstPeriod(LocalDate firstPeriod) {
        this.firstPeriod = firstPeriod;
    }
    public LocalDate getLastPeriod() {
        return lastPeriod;
    }
    public void setLastPeriod(LocalDate lastPeriod) {}
}
