package com.talipov;

/**
 * Created by Марсель on 07.02.2017.
 */
public class Totalizer {
    /**
     * Результат операции
     */
    private int result = 0;

    private boolean checkRule(int num) {
        return num > 0 && num % 2 == 0;
    }

    public void add(int num) {
        if (!checkRule(num)) {
            return;
        }
        result += num;
        System.out.println("\rResult: " + result + " Num:" + num);
    }

    public int getResult() {
        return result;
    }
}
