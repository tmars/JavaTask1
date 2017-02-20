package com.talipov.worker;

import com.talipov.totalizer.TotalizerInterface;

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
    public void work(String[] resources) {
    }

    public TotalizerInterface getTotalizer() {
        return totalizer;
    }
}
