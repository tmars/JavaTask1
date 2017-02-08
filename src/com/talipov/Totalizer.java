package com.talipov;

/**
 * Created by Марсель on 07.02.2017.
 */
public class Totalizer {
    /**
     * Результат операции
     */
    private int result = 0;
    private boolean isActive = true;

    private boolean checkRule(int num) {
        return num > 0 && num % 2 == 0;
    }

    public synchronized void add(int num) {
        if (!checkRule(num)) {
            return;
        }
        result += num;
        System.out.println("\rСумма: " + result + ", Число: " + num);
    }

    public int getResult() {
        return result;
    }

    public boolean isActive() {
        return isActive;
    }

    public synchronized void setActive(boolean active) {
        isActive = active;
    }
}
