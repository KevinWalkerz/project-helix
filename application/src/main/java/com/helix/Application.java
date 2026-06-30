package com.helix;

import com.vaadin.flow.theme.aura.Aura;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.server.AppShellSettings;

@SpringBootApplication
@StyleSheet(Aura.STYLESHEET)
// Your custom styles
@StyleSheet("styles.css")
@Push
public class Application implements AppShellConfigurator {

    @Override
    public void configurePage(AppShellSettings settings) {
        settings.addFavIcon("icon", "/icons/icon_small.png", "32x32");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
