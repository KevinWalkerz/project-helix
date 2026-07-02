package com.helix.ui.anonymous;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "forgot-password", autoLayout = false)
@PageTitle("Forgot Password")
@AnonymousAllowed
public class ForgotPasswordView extends Div {

    public ForgotPasswordView() {
        EmailField emailField = new EmailField("Email");
        emailField.setWidthFull();

        Button sendOtpButton = new Button("Send OTP");
        sendOtpButton.addClickListener(event -> Notification.show("OTP sent successfully."));

        VerticalLayout form = new VerticalLayout(
                new H2("Forgot Password"),
                emailField,
                sendOtpButton
        );
        form.setWidth("360px");
        form.setPadding(false);
        form.setSpacing(true);

        addClassName("forgot-password-view");
        getStyle()
                .set("min-height", "100vh")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("background", "var(--aura-background-gradient, var(--lumo-contrast-5pct))");

        add(form);
    }
}
