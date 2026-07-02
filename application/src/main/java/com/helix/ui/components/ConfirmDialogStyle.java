package com.helix.ui.components;

import com.helix.zibrary.enumeration.security.AccessType;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;


public class ConfirmDialogStyle extends ConfirmDialog {

    public ConfirmDialogStyle(String header, String message, AccessType access, String yesCaption) {
        setMaxWidth("600px");
        setCloseOnEsc(true);

        // Buttons
        setConfirmText(yesCaption);
        setConfirmButtonTheme("primary");
        setCancelable(true);
        setCancelText("Cancel");
        setCancelButtonTheme("tertiary-text");

        // Content
        add(createContent(access, header, message));
    }

    public static ConfirmDialogStyle show(String header, String message, AccessType access, String yesCaption) {
        ConfirmDialogStyle confirmDialogStyle = new ConfirmDialogStyle(header, message, access, yesCaption);
        confirmDialogStyle.open();
        return confirmDialogStyle;
    }

    private VerticalLayout createContent(AccessType access, String header, String message) {
        Icon icon = VaadinIcon.INFO_CIRCLE.create();

        if(access.equals(AccessType.DELETE)){
            icon = VaadinIcon.WARNING.create();
            setConfirmButtonTheme("error primary");
        }

        icon.getStyle()
                .set("color", "var(--lumo-primary-color)")
                .set("margin-top", "var(--lumo-space-xs)");

        H2 headline = new H2(header);
        headline.getStyle()
                .set("margin", "0")
                .set("font-size", "var(--lumo-font-size-xl)")
                .set("font-weight", "600");

        Html text = new Html("<span>" + message.replace("\n", "<br>") + "</span>");
        text.getStyle()
                .set("margin-top", "var(--lumo-space-s)")
                .set("margin-bottom", "0")
                .set("color", "var(--lumo-secondary-text-color)");

        VerticalLayout textLayout = new VerticalLayout(headline, text);
        textLayout.setPadding(false);
        textLayout.setSpacing(false);
        textLayout.setWidthFull();

        HorizontalLayout wrapper = new HorizontalLayout(icon, textLayout);
        wrapper.setPadding(true);
        wrapper.setSpacing(true);
        wrapper.setAlignItems(FlexComponent.Alignment.START);
        wrapper.setWidthFull();

        VerticalLayout dialogLayout = new VerticalLayout(wrapper);
        dialogLayout.setPadding(false);
        dialogLayout.setSpacing(false);
        dialogLayout.setAlignItems(FlexComponent.Alignment.STRETCH);
        dialogLayout.setWidthFull();

        return dialogLayout;
    }
}