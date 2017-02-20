package com.talipov.totalizer;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Created by Марсель on 19.02.2017.
 */
public class ReentrantLockTotalizer implements TotalizerInterface {

    /**
     * Логгер
     */
    private static final Logger logger = Logger.getLogger(ReentrantLockTotalizer.class);

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
     * Блокировка на добавление
     */
    Lock lockAdd = new ReentrantLock();

    /**
     * Блокировка на активность
     */
    Lock lockActive = new ReentrantLock();

    /**
     * @inheritDoc
     */
    public void add(int num) {

        if (num <= 0 || num % 2 == 1) {
            return;
        }

        lockAdd.lock();
        try {
            result += num;
        } finally {
            lockAdd.unlock();
        }

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
        lockActive.lock();
        try {
            isActive = active;
        } finally {
            lockActive.unlock();
        }
    }
}
