package com.talipov;

import java.util.Scanner;

/**
 * Created by Марсель on 07.02.2017.
 */
public class Parser {

    private Scanner input;

    public Parser(Scanner input) {
        this.input = input;
    }

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
