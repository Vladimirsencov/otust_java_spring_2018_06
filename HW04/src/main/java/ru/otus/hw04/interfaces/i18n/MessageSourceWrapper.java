package ru.otus.hw04.interfaces.i18n;

public interface MessageSourceWrapper {
    String getMsg(String messageName);

    String getMsg(String messageName, String param);

    String getMsg(String messageName, Object... params);

}
