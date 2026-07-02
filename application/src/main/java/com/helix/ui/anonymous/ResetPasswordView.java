package com.helix.ui.anonymous;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "reset-password", autoLayout = false)
@PageTitle("Reset Password")
@AnonymousAllowed
public class ResetPasswordView extends Div {

    public ResetPasswordView() {
        PasswordField newPasswordField = new PasswordField("New Password");
        newPasswordField.setWidthFull();

        PasswordField reEnterPasswordField = new PasswordField("Re-enter Password");
        reEnterPasswordField.setWidthFull();

        Button confirmButton = new Button("Confirm");
        confirmButton.addClickListener(event -> Notification.show("Password reset successfully."));

        VerticalLayout form = new VerticalLayout(
                new H2("Reset Password"),
                newPasswordField,
                reEnterPasswordField,
                confirmButton
        );
        form.setWidth("360px");
        form.setPadding(false);
        form.setSpacing(true);

        addClassName("reset-password-view");
        getStyle()
                .set("min-height", "100vh")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("background", "var(--aura-background-gradient, var(--lumo-contrast-5pct))");

        add(form);
    }
}
