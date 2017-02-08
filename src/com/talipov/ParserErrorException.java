package com.talipov;

/**
 * Created by Марсель on 07.02.2017.
 */
public class ParserErrorException extends Exception {

    public ParserErrorException(String s) {
        super("Неверные данные: '" + s + "'");
    }
}
