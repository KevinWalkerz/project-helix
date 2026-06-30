package com.helix.ui.components;

import com.helix.enumeration.NotificationType;
import com.vaadin.flow.component.Html;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class NotificationStyle extends Notification {

    public static Notification show(NotificationType type, String message){
        Notification notification = new Notification();
        Html text = new Html("<span>" + message.replace("\n", "<br>") + "</span>");

        Button closeButton = new Button(new Icon("lumo", "cross"));
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        closeButton.setAriaLabel("Close");
        closeButton.addClickListener(event -> notification.close());

        HorizontalLayout layout = new HorizontalLayout();
        layout.add(text, closeButton);
        layout.setAlignItems(FlexComponent.Alignment.CENTER);

        notification.addThemeVariants(type.variant);
        notification.setDuration(type.duration);
        notification.add(layout);
        notification.open();
        notification.setPosition(Position.TOP_CENTER);

        return notification;
    }

}
