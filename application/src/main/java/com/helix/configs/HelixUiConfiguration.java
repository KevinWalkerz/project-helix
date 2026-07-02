package com.helix.configs;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.page.ColorScheme;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import org.springframework.stereotype.Component;

@Component
public class HelixUiConfiguration implements VaadinServiceInitListener {

    public static final String APP_NAME = "Helix";
    public static final String DARK_MODE_STORAGE_KEY = "helix-dark-mode";

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            UI ui = uiEvent.getUI();

            ui.getPage().executeJs("return localStorage.getItem($0) === 'true';", DARK_MODE_STORAGE_KEY)
                    .then(Boolean.class, isDark -> {
                        if (Boolean.TRUE.equals(isDark)) {
                            ui.getPage().setColorScheme(ColorScheme.Value.DARK);
                        }
                    });

            ui.addAfterNavigationListener(this::updateDocumentTitle);
        });
    }

    private void updateDocumentTitle(AfterNavigationEvent event) {
        UI ui = UI.getCurrent();
        if (ui == null) {
            return;
        }

        Object view = event.getActiveChain().get(0);
        String title = "";

        if (view != null) {
            PageTitle annotation = view.getClass().getAnnotation(PageTitle.class);
            if (annotation != null) {
                title = annotation.value();
            }
        }

        ui.getPage().setTitle(
                title == null || title.isEmpty()
                        ? APP_NAME
                        : APP_NAME + " | " + title
        );
    }

    public static void setDarkMode(boolean dark) {
        UI ui = UI.getCurrent();
        if (ui == null) {
            return;
        }

        ui.getPage().setColorScheme(dark ? ColorScheme.Value.DARK : ColorScheme.Value.LIGHT);
        ui.getPage().executeJs("localStorage.setItem($0, $1);", DARK_MODE_STORAGE_KEY, dark);
    }
}
