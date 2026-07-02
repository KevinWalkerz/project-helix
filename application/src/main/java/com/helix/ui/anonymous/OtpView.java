package com.helix.ui.anonymous;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "otp", autoLayout = false)
@PageTitle("Verify OTP")
@AnonymousAllowed
public class OtpView extends Div {

    public OtpView() {
        TextField codeField = new TextField("Code");
        codeField.setWidthFull();

        Button verifyButton = new Button("Verify");
        verifyButton.addClickListener(event -> Notification.show("OTP verified successfully."));

        VerticalLayout form = new VerticalLayout(
                new H2("Verify OTP"),
                codeField,
                verifyButton
        );
        form.setWidth("360px");
        form.setPadding(false);
        form.setSpacing(true);

        addClassName("otp-view");
        getStyle()
                .set("min-height", "100vh")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("background", "var(--aura-background-gradient, var(--lumo-contrast-5pct))");

        add(form);
    }
}
