package com.talipov;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by Марсель on 07.02.2017.
 * Общий обработчик данных всех ресурсов
 */
public class Totalizer {

    /**
     * Логгер
     */
    private static final Logger logger = Logger.getLogger(Totalizer.class);

    /**
     * Результат операции
     */
    private volatile int result = 0;

    /**
     * Флаг, используется для остановки потоков
     * в случае ошибки при мультипоточной обработке
     */
    private volatile boolean isActive = true;

    /**
     * Инициализация логгера
     */
    static {
        PropertyConfigurator.configure("src/main/resources/log4j.xml");
    }

    /**
     * Добавляет элемент в обработку
     * @param num входной элемент
     */
    public synchronized void add(int num) {
        if (num <= 0 || num % 2 == 1) {
            return;
        }
        result += num;
        logger.trace("Сумма: " + result + ", Число: " + num);
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
