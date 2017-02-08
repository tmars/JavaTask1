package com.talipov;

/**
 * Created by Марсель on 07.02.2017.
 * Общий обработчик данных всех ресурсов
 */
public class Totalizer {

    /**
     * Результат операции
     */
    private int result = 0;

    /**
     * Флаг, используется для остановки потоков
     * в случае ошибки при мультипоточной обработке
     */
    private boolean isActive = true;

    /**
     * Добавляет элемент в обработку
     * @param num входной элемент
     */
    public synchronized void add(int num) {
        if (num <= 0 || num % 2 == 1) {
            return;
        }
        result += num;
        System.out.println("\rСумма: " + result + ", Число: " + num);
    }

    /**
     * Возвращает результат обработки данных
     * @return результат обработки
     */
    public int getResult() {
        return result;
    }

    /**
     * Активен ли обработчик
     * @return флаг активности
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Установка флага активности обработчика
     * @param active флага активности
     */
    public synchronized void setActive(boolean active) {
        isActive = active;
    }
}
