package com.helix.ui;

import com.flowingcode.addons.ycalendar.YearMonthField;
import com.helix.base.AppData;
import com.helix.security.AuthenticatedUser;
import com.helix.zibrary.data.security.entities.UserCredentials;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.helix.configs.HelixUiConfiguration;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
@PermitAll
public final class MainLayout extends AppLayout implements RouterLayout, AfterNavigationObserver {

    H2 viewTitle;
    Button themeToggle;

    YearMonthField periodField;

    private final AuthenticatedUser authenticatedUser;

    AppData appData;
    Authentication authentication;

    public MainLayout(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;

        authentication = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication());

        appData = VaadinSession.getCurrent().getAttribute(AppData.class);
        if (appData == null) {
            appData = new AppData();
            VaadinSession.getCurrent().setAttribute(AppData.class, appData);
        }

        setPrimarySection(Section.DRAWER);

        addHeaderContent();

        addToDrawer(createApplicationHeader(),
                createApplicationDrawer(),
                createApplicationFooter());
    }

    private void addHeaderContent(){
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.SMALL, LumoUtility.Margin.NONE);

        HorizontalLayout vLayout = new HorizontalLayout();
        vLayout.addClassName("period-section");
        vLayout.setAlignItems(FlexComponent.Alignment.END);
        periodField = new YearMonthField();
        periodField.addClassName("period-field");
        periodField.addValueChangeListener(event -> changeValueDatePeriod());
        vLayout.add(createLanguageSwitch(), createThemeToggle(), periodField);

        HorizontalLayout mainHeaderBar = new HorizontalLayout(toggle, viewTitle, vLayout);
        mainHeaderBar.addClassName("app-navbar-main");
        mainHeaderBar.setWidthFull();
        mainHeaderBar.setSpacing(true);
        mainHeaderBar.setPadding(false);
        mainHeaderBar.setAlignItems(FlexComponent.Alignment.CENTER);
        mainHeaderBar.setFlexGrow(1, viewTitle);
        mainHeaderBar.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        VerticalLayout navbar = new VerticalLayout(mainHeaderBar);
        navbar.addClassName("app-navbar-stack");
        navbar.setPadding(false);
        navbar.setSpacing(false);
        navbar.setWidthFull();

        addToNavbar(true, navbar);
        changeValueDatePeriod();
    }

    private Button createThemeToggle() {
        themeToggle = new Button();
        themeToggle.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        themeToggle.addClassName("theme-toggle-button");
        themeToggle.setAriaLabel("Toggle dark mode");
        updateThemeToggleIcon(false);

        themeToggle.addClickListener(event -> {
            boolean darkMode = !themeToggle.getElement().getProperty("data-dark", false);
            HelixUiConfiguration.setDarkMode(darkMode);
            updateThemeToggleIcon(darkMode);
        });

        themeToggle.getElement().executeJs(
                "return localStorage.getItem($0) === 'true';",
                HelixUiConfiguration.DARK_MODE_STORAGE_KEY
        ).then(Boolean.class, isDark -> updateThemeToggleIcon(Boolean.TRUE.equals(isDark)));

        return themeToggle;
    }

    private void updateThemeToggleIcon(boolean darkMode) {
        themeToggle.setIcon(new Icon(darkMode ? VaadinIcon.SUN_O : VaadinIcon.MOON_O));
        themeToggle.getElement().setProperty("data-dark", darkMode);
        themeToggle.setTooltipText(darkMode ? "Switch to light mode" : "Switch to dark mode");
    }

    private Component createLanguageSwitch() {
        HorizontalLayout switcher = new HorizontalLayout();
        switcher.addClassName("language-switch");
        switcher.setPadding(false);
        switcher.setSpacing(false);
        switcher.setAlignItems(FlexComponent.Alignment.CENTER);

        Locale currentLocale = VaadinSession.getCurrent().getLocale();
        String language = currentLocale == null ? Locale.ENGLISH.getLanguage() : currentLocale.getLanguage();

        switcher.add(
                createLanguageButton("EN", "flag-uk", Locale.ENGLISH, !"id".equalsIgnoreCase(language)),
                createLanguageButton("ID", "flag-id", new Locale("id"), "id".equalsIgnoreCase(language))
        );
        return switcher;
    }

    private Button createLanguageButton(String label, String flagClassName, Locale locale, boolean active) {
        Span flag = new Span();
        flag.addClassNames("language-flag", flagClassName);

        Span text = new Span(label);
        text.addClassName("language-label");

        Button button = new Button();
        button.setIcon(flag);
        button.setText(label);
        button.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        if (active) {
            button.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        }
        button.addClickListener(event -> changeLanguage(locale));
        return button;
    }

    private void changeLanguage(Locale locale) {
        VaadinSession.getCurrent().setLocale(locale);
        UI.getCurrent().setLocale(locale);
        UI.getCurrent().getPage().executeJs("window.location.reload()");
    }

    private Component createApplicationHeader() {
        Image logo = new Image("images/logo.png", "Helix");
        logo.setWidth("150px");
        logo.addClassName("drawer-logo");

        HorizontalLayout header = new HorizontalLayout(logo);
        header.addClassName("drawer-brand");
        header.setAlignItems(FlexComponent.Alignment.CENTER);
        header.setPadding(true);
        return header;
    }

    private Component createApplicationDrawer() {
        var scroller = new Scroller(createSideNav());
        scroller.addThemeVariants(ScrollerVariant.OVERFLOW_INDICATORS);
        return scroller;
    }

    /**
     * The logged-in user's account menu (or the sign-in link), pinned at the
     * bottom of the drawer.
     */
    private Component createApplicationFooter() {
        var layout = new Footer();

        Optional<UserCredentials> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            UserCredentials user = maybeUser.get();

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(createAccountComponent(user));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Ganti Password", e -> {
//                PasswordChangePopup dialog = new PasswordChangePopup(user.getId(), userService);
//
//                dialog.addOpenedChangeListener(event -> {
//                    if(!event.isOpened()){
//                        if(dialog.isOkSelected()){
//                            saveChangedPassword(dialog.getModifiedUser());
//                        }
//                    }
//                });
//
//                dialog.open();
            });
            userName.getSubMenu().addItem("Log out", e -> {
//                YesCancelConfirmDialog dialog = new YesCancelConfirmDialog("Apa anda yakin?","Apakah anda yakin ingin logout di akun ini?", "Logout");
//                dialog.open();
//                dialog.addConfirmListener(event ->{
//                    authenticatedUser.logout();
//                });
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

//    private static Renderer<UserCredentials> createAccountRenderer() {
//        return LitRenderer
//                .<UserCredentials> of(
//                        """
//                                <div class="person-item">
//                                  <vaadin-avatar img="${item.pictureUrl}" name="${item.fullName}" style="--vaadin-avatar-size: 2.25rem"></vaadin-avatar>
//                                  <span>${item.fullName}</span>
//                                  <span>${item.email}</span>
//                                </div>
//                                """)
//                .withProperty("pictureUrl", null)
//                .withProperty("fullName", UserCredentials::getRegisteredName)
//                .withProperty("email", UserCredentials::getEmail);
//    }

    private static Component createAccountComponent(UserCredentials item) {

        Avatar avatar = new Avatar(item.getRegisteredName());
        avatar.setImage("/images/default-user.jpeg");
        avatar.getStyle().set("--vaadin-avatar-size", "2.25rem");

        Span name = new Span(item.getRegisteredName());
        name.getStyle()
                .set("font-weight", "600");

        Span email = new Span(item.getEmail());
        email.getStyle()
                .set("font-size", "0.75rem")
                .set("font-style", "italic")
                .set("color", "var(--lumo-secondary-text-color)");

        VerticalLayout textLayout = new VerticalLayout(name, email);
        textLayout.setPadding(false);
        textLayout.setSpacing(false);
        textLayout.setMargin(false);

        HorizontalLayout layout = new HorizontalLayout(avatar, textLayout);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setSpacing(true);

        return layout;
    }

    private void changeValueDatePeriod(){
        AppData appdata = VaadinSession.getCurrent().getAttribute(AppData.class);

        if(appdata == null){
            appdata = new AppData();
            VaadinSession.getCurrent().setAttribute(AppData.class, appdata);
        }

        appData.setFirstPeriod(periodField.getValue().atDay(1));
        appData.setLastPeriod(periodField.getValue().atEndOfMonth());
    }

    private SideNav createSideNav() {
        var nav = new SideNav();
        nav.setMinWidth(200, Unit.PIXELS);
        MenuConfiguration.getMenuEntries().forEach(entry -> nav.addItem(createSideNavItem(entry)));
        return nav;
    }

    private SideNavItem createSideNavItem(MenuEntry menuEntry) {
        if (menuEntry.icon() != null) {
            Component icon = null;
            if (menuEntry.icon().contains(".svg")) {
                icon = new SvgIcon(menuEntry.icon());
            } else {
                icon = new Icon(menuEntry.icon());
            }
            return new SideNavItem(menuEntry.title(), menuEntry.menuClass(), icon);
        } else {
            return new SideNavItem(menuEntry.title(), menuEntry.menuClass());
        }
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {

        // IMPORTANT: getActiveChain() is ordered leaf-view FIRST, outermost layout LAST.
        // get(size() - 1) was grabbing MainLayout itself (no @PageTitle there), which is
        // why the "Helix | X" logic never actually picked up the view's title.
        // The actual routed view/leaf component is at index 0.
        Object view = event.getActiveChain().get(0);

        String title = "";

        if (view != null) {
            PageTitle annotation = view.getClass().getAnnotation(PageTitle.class);
            if (annotation != null) {
                title = annotation.value();
            }
        }

        viewTitle.setText(title);
    }
}