package com.talipov.totalizer;

/**
 * Created by Марсель on 19.02.2017.
 * Интерфейс для работы общим с обработчиком данных
 */
public interface TotalizerInterface {
    /**
     * Добавляет элемент в обработку
     * @param num входной элемент
     */
     void add(int num);

    /**
     * Возвращает результат обработки данных
     * @return результат обработки
     */
    int getResult();

    /**
     * Активен ли обработчик
     * @return флаг активности
     */
    boolean isActive();

    /**
     * Установка флага активности обработчика
     * @param active флага активности
     */
    void setActive(boolean active);
}
