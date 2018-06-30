package ru.otus.hw01.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw01.interfaces.i18n.MessageSourceWrapper;

import java.util.Locale;

@Service("messageSourceWrapper")
public class MessageSourceWrapperImpl implements MessageSourceWrapper {

    private final Locale locale;
    private final MessageSource ms;

    public MessageSourceWrapperImpl(@Autowired  MessageSource ms, @Value("${locale.name}") String localeName) {
        this.ms = ms;
        this.locale = new Locale(localeName, "", "");
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
