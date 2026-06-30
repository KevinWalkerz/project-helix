package com.helix.ui.dashboard;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.helix.ui.MainLayout;
import com.vaadin.flow.router.DynamicPageTitle;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "", layout = MainLayout.class)
@PageTitle("Helix | Dashboard")
@PermitAll
public class DashboardView extends VerticalLayout {

    public DashboardView() {
        add(new H1("Welcome to your new application"));
        add(new Paragraph("This is the dashboard view"));
    }
}
