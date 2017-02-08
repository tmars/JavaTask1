package com.talipov;

import java.util.Scanner;

/**
 * Created by Марсель on 07.02.2017.
 * Парсер целых чисел из данных ресурса
 */
public class Parser {

    /**
     * Входные данные
     */
    private Scanner input;

    /**
     * Конструктов
     * @param input входные данные
     */
    public Parser(Scanner input) {
        this.input = input;
    }

    /**
     * Возвращает следующее целое число если возможно,
     * если данные не корректны выбрасывает исключение ParserErrorException
     * @return следующее целове число из выходных данных
     * @throws ParserErrorException выбрасывается в случае не корректных данных
     */
    public Integer getNext() throws ParserErrorException {
        if (input.hasNext()) {
            if (input.hasNextInt()) {
                return input.nextInt();
            } else {
                String s = input.next();
                input.close();

                throw new ParserErrorException(s);
            }
        } else {
            input.close();
        }

        return null;
    }
}
