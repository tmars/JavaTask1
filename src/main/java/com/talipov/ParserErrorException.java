package com.talipov;

/**
 * Created by Марсель on 07.02.2017.
 * Исключение при парсинге ресурса
 */
public class ParserErrorException extends Exception {

    /**
     * Конструктор
     * @param s строка сообщения
     */
    public ParserErrorException(String s) {
        super("Неверные данные: '" + s + "'");
    }
}
