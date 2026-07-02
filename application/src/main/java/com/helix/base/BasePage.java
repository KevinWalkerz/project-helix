package com.helix.base;

import com.helix.configs.I18n;
import com.helix.security.AuthenticatedUser;
import com.helix.zibrary.data.security.entities.UserCredentials;
import com.helix.zibrary.data.security.services.SecurityService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.HttpStatusCode;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BasePage extends VerticalLayout {

    public BasePage() {
        this.setSizeFull();
    }

    //Check language
    public String selectedLanguage(){
        return VaadinSession.getCurrent().getLocale().getLanguage();
    }

    //Check User
    public Map<String, String> getUsername(SecurityService securityService, I18n i18n){
        Map<String, String> returnUserName = new HashMap<>();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserCredentials> user = securityService.getUserByEmail(authentication.getName()).or(() -> securityService.getUserByUsername(authentication.getName()));
        if(user.isEmpty()){
            returnUserName.put("status", "error");
            returnUserName.put("message", i18n.t("error.user.not_found", selectedLanguage()));
            return returnUserName;
        }

        if(!user.get().isActive()){
            returnUserName.put("status", "error");
            returnUserName.put("message", i18n.t("error.user.inactive", selectedLanguage()));
            return returnUserName;
        }

        returnUserName.put("status", "success");
        returnUserName.put("registeredName", user.get().getRegisteredName());
        return returnUserName;
    }

    public LocalDate getFirstPeriod(){
        return VaadinSession.getCurrent().getAttribute(AppData.class).getFirstPeriod();
    }

    public LocalDate getLastPeriod(){
        return VaadinSession.getCurrent().getAttribute(AppData.class).getLastPeriod();
    }

}
