package ru.otus.hw04;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import ru.otus.hw04.interfaces.i18n.MessageSourceWrapper;

import java.util.Locale;

@Getter
@Setter
@Component
@ConfigurationProperties("application")
public class ApplicationSettings {

    private MessageSourceWrapper messageSourceWrapper;
    private String csvFileLocationTemplate;
    private Locale locale = Locale.forLanguageTag("ru-RU");
}
