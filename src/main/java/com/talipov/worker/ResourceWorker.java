package com.talipov.worker;

import com.talipov.ResourceNotFoundException;
import com.talipov.parser.Parser;
import com.talipov.totalizer.TotalizerInterface;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Марсель on 19.02.2017.
 * Супер-класс для обработки всех ресурсов
 */
public class ResourceWorker {

    /**
     * Общий обработчик данных всех ресурсов
     */
    protected TotalizerInterface totalizer;

    public ResourceWorker(TotalizerInterface totalizer) {
        this.totalizer = totalizer;
    }

    /**
     * Запуск потоков обработки ресурсов
     * @param resources список ресурсов: URL или путь до файла
     */
    public void work(String[] resources, Class<? extends Parser> parserClass) {
    }

    public TotalizerInterface getTotalizer() {
        return totalizer;
    }

    protected Parser createParser(Class parserClass, String resource) throws ResourceNotFoundException {
        Constructor constructor;
        try {
            constructor = parserClass.getConstructor(String.class);
        } catch (NoSuchMethodException e) {
            return null;
        }

        try {
            return (Parser) constructor.newInstance(resource);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            return null;
        }
    }
}
