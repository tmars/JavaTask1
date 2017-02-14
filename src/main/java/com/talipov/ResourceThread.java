package com.talipov;

import java.io.FileNotFoundException;

/**
 * Created by Марсель on 08.02.2017.
 * Поток обработки ресурса
 */
public class ResourceThread extends Thread {

    /**
     * Ридер данных из ресурса
     */
    private ResourceReader reader;

    /**
     * Конструктор
     * @param reader Ридер данных из ресурса
     */
    public ResourceThread(ResourceReader reader) {
        this.reader = reader;
    }

    /**
     * Запуск процесса чтения данных
     */
    @Override
    public void run() {
        this.reader.read();
    }
}
