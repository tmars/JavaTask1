package com.talipov;

import java.util.ArrayList;

/**
 * Created by Марсель on 07.02.2017.
 */
public class Parser {

    /**
     * Принимает на вход строки и возвращает список всех целых чисел,
     * либо выбрасывает исключение если в строке есть недопустимые символы
     * @param input входная строка в которой осуществляется поиск
     * @return список найденных целых чисел
     * @throws ParserErrorException бросает исключение если встретились некорректные данные
     */
    public ArrayList<Integer> parse(String input) throws ParserErrorException {
        String[] parts = input.trim().split("\\s+");
        ArrayList<Integer> result = new ArrayList<>(parts.length);
        for (String part : parts) {
            try {
                result.add(Integer.parseInt(part));
            } catch (NumberFormatException e) {
                throw new ParserErrorException("Ошибка при обработке данных");
            }
        }

        return result;
    }

}
