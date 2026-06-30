package com.helix.ui.account.popup;

import com.helix.configs.I18n;
import com.helix.zibrary.data.security.entities.UserCredentials;
import com.helix.zibrary.data.security.services.SecurityService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinSession;

import java.util.Locale;
import java.util.UUID;

public class PasswordChangePopup extends Dialog {

    UUID id;

    TextField txtUsername;
    TextField txtEmail;

    PasswordField txtOldPassword;
    PasswordField txtNewPassword;
    PasswordField txtConfirmPassword;

    Button btnSave;
    Button btnCancel;

    UserCredentials userSnapshot;
    UserCredentials userEdited;

    boolean isOkClicked = false;

    private final I18n i18n;
    private final SecurityService securityService;

    public PasswordChangePopup(UUID id,
                               I18n i18n,

                               SecurityService securityService) {
        this.id = id;
        this.i18n = i18n;
        this.securityService = securityService;

        setWidth("400px");
        setHeight("400px");
        isCloseOnEsc();
        setHeaderTitle(i18n.t("header.password.change", getCurrentLanguage()));

        createComponent();
        createLayoutButton();
        loadData();
    }

    public void createComponent() {

    }

    public void createLayoutButton() {

    }

    public void loadData() {

    }

    private String getCurrentLanguage() {
        Locale locale = VaadinSession.getCurrent().getLocale();
        return locale == null ? Locale.ENGLISH.getLanguage() : locale.getLanguage();
    }

}
