package com.helix.api.configs.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class I18n {
    private final MessageSource messageSource;

    public String t(String key, String lang, Object... args) {
        Locale locale = "id".equalsIgnoreCase(lang) ? new Locale("id") : Locale.ENGLISH;
        return messageSource.getMessage(key, args, locale);
    }
}
