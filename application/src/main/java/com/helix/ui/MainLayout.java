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
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.SvgIcon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.*;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
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
public final class MainLayout extends AppLayout implements RouterLayout{

    H2 viewTitle;

    YearMonthField periodField;

    private final AuthenticatedUser authenticatedUser;

    AppData appData;
    Authentication authentication;

    public MainLayout(AuthenticatedUser authenticatedUser) {
        this.authenticatedUser = authenticatedUser;

        authentication = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication());
        appData = VaadinSession.getCurrent().getAttribute(AppData.class);
        if(appData == null){
            appData = new AppData();
            VaadinSession.getCurrent().setAttribute(AppData.class, appData);
        }

        setPrimarySection(Section.DRAWER);
        addHeaderContent();
        addToDrawer(createApplicationHeader(), createApplicationDrawer(), createApplicationFooter());
    }

    private void addHeaderContent(){
        DrawerToggle toggle = new DrawerToggle();
        toggle.getElement().setAttribute("aria-label", "Menu toggle");

        viewTitle = new H2();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        VerticalLayout vLayout = new VerticalLayout();
        vLayout.addClassName("period-section");
        vLayout.setAlignItems(FlexComponent.Alignment.END);
        periodField = new YearMonthField();
        periodField.addClassName("period-field");
        periodField.addValueChangeListener(event ->{
            changeValueDatePeriod();
        });

        vLayout.add(periodField);

        HorizontalLayout mainHeaderBar = new HorizontalLayout(toggle, viewTitle, vLayout);
        mainHeaderBar.addClassName("app-navbar-main");
        mainHeaderBar.setWidthFull();
        mainHeaderBar.setSpacing(false);
        mainHeaderBar.setPadding(false);
        mainHeaderBar.setAlignItems(FlexComponent.Alignment.CENTER);
        mainHeaderBar.expand(viewTitle);

        HorizontalLayout menuHeaderBar = createMenuHeaderBar();

        VerticalLayout navbar = new VerticalLayout(menuHeaderBar, mainHeaderBar);
        navbar.addClassName("app-navbar-stack");
        navbar.setPadding(false);
        navbar.setSpacing(false);
        navbar.setWidthFull();

        addToNavbar(true, navbar);
        changeValueDatePeriod();
    }

    private HorizontalLayout createMenuHeaderBar() {
        HorizontalLayout menuHeaderBar = new HorizontalLayout();

        Button btnSetup = new Button("Setup");
        btnSetup.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        btnSetup.addClassName("app-header-menu-button");
//        btnSetup.addClickListener(event -> UI.getCurrent().navigate(""));
        menuHeaderBar.add(btnSetup);

        Button btnMaster = new Button("Master");
        btnMaster.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        btnMaster.addClassName("app-header-menu-button");
//        btnSetup.addClickListener(event -> UI.getCurrent().navigate(""));
        menuHeaderBar.add(btnMaster);

        menuHeaderBar.add(createLanguageSwitch());

        menuHeaderBar.addClassName("app-navbar-actions");
        menuHeaderBar.setWidthFull();
        menuHeaderBar.setAlignItems(FlexComponent.Alignment.CENTER);
        menuHeaderBar.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
        return menuHeaderBar;
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
        button.addClassName("language-button");
        if (active) {
            button.addClassName("active");
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

    private Component createApplicationFooter() {
        var layout = new Footer();

        Optional<UserCredentials> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            UserCredentials user = maybeUser.get();

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            div.add(user.getRegisteredName());
//            div.add(new Icon("lumo", "dropdown"));
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

    private void changeValueDatePeriod(){
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
}
