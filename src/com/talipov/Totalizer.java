package com.talipov;

/**
 * Created by Марсель on 07.02.2017.
 */
public class Totalizer {
    /**
     * Результат операции
     */
    private int total = 0;

    private boolean checkRule(int num) {
        return num > 0 && num % 2 == 0;
    }

    public void add(int num) {
        if (checkRule(num) == false) {
            return;
        }
        total += num;
        System.out.println("Result: " + Integer.toString(total));
    }

    public int getResult() {
        return total;
    }
}
