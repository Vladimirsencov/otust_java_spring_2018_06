package ru.otus.hw05;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Getter
@Setter
@Component
@ConfigurationProperties("application")
public class ApplicationSettings {
    private Locale locale = Locale.forLanguageTag("ru-RU");
}
