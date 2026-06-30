package com.helix.ui.anonymous;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

@Route(value = "login", autoLayout = false)
@PageTitle("Login | Helix")
@AnonymousAllowed
public class LoginView extends Div implements BeforeEnterObserver {

    private final LoginForm loginForm;

    public LoginView() {
        Image logo = new Image("images/logo.png", "Helix Logo");

        loginForm = new LoginForm();
        loginForm.setAction("login");
        loginForm.setI18n(createLoginI18n());
        loginForm.addForgotPasswordListener(event -> UI.getCurrent().navigate("forgot-password"));

        addClassName("login-view");
        getStyle()
                .set("min-height", "100vh")
                .set("display", "flex")
                .set("align-items", "center")
                .set("justify-content", "center")
                .set("background", "var(--aura-background-gradient, var(--lumo-contrast-5pct))");

        add(loginForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (event.getLocation().getQueryParameters().getParameters().containsKey("error")) {
            loginForm.setError(true);
        }
    }

    private LoginI18n createLoginI18n() {
        LoginI18n i18n = LoginI18n.createDefault();

        LoginI18n.Header header = new LoginI18n.Header();
        header.setTitle("Helix");
        header.setDescription("Sign in to continue");
        i18n.setHeader(header);

        LoginI18n.Form form = i18n.getForm();
        form.setUsername("Email / Username");
        form.setPassword("Password");
        form.setSubmit("Login");
        form.setForgotPassword("Forgot password");
        i18n.setForm(form);

        LoginI18n.ErrorMessage errorMessage = i18n.getErrorMessage();
        errorMessage.setTitle("Login failed");
        errorMessage.setMessage("Check your email, username, and password, then try again.");
        i18n.setErrorMessage(errorMessage);

        return i18n;
    }
}
