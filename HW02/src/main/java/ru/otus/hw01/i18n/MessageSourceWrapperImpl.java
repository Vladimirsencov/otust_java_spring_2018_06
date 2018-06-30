package ru.otus.hw01.i18n;

import org.springframework.context.MessageSource;
import ru.otus.hw01.interfaces.i18n.MessageSourceWrapper;

import java.util.Locale;

public class MessageSourceWrapperImpl implements MessageSourceWrapper {

    private final Locale locale;
    private final MessageSource ms;

    public MessageSourceWrapperImpl(MessageSource ms, Locale locale) {
        this.ms = ms;
        this.locale = locale;
    }

    public String getMsg(String messageName) {
        return ms.getMessage(messageName, null, locale);
    }

    public String getMsg(String messageName, String param) {
        return ms.getMessage(messageName, new String[] {param}, locale);
    }

    public String getMsg(String messageName, Object... params) {
        return ms.getMessage(messageName, params, locale);
    }

}
