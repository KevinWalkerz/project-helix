package com.helix.enumeration;

import com.vaadin.flow.component.notification.NotificationVariant;

public enum NotificationType {
    SUCCESS(NotificationVariant.SUCCESS, 3000),
    WARNING(NotificationVariant.WARNING, 3000),
    ERROR(NotificationVariant.ERROR, 10000);

    public NotificationVariant variant;
    public int duration;

    NotificationType(NotificationVariant variant, int duration){
        this.variant = variant;
        this.duration = duration;
    }
}
