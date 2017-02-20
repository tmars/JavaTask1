package com.talipov.totalizer;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by Марсель on 07.02.2017.
 * Общий обработчик данных всех ресурсов
 */
public class Totalizer implements TotalizerInterface {

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
     * @inheritDoc
     */
    public synchronized void add(int num) {
        if (num <= 0 || num % 2 == 1) {
            return;
        }
        result += num;
        logger.trace("Сумма: " + result + ", Число: " + num);
    }

    /**
     * @inheritDoc
     */
    public int getResult() {
        return result;
    }

    /**
     * @inheritDoc
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @inheritDoc
     */
    public synchronized void setActive(boolean active) {
        isActive = active;
    }
}
