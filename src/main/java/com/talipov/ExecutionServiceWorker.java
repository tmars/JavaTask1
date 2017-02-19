package com.talipov;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Марсель on 19.02.2017.
 */
public class ExecutionServiceWorker {
    /**
     * Логгер
     */
    private static final Logger logger = Logger.getLogger(ResourcePoolWorker.class);

    /**
     * Общий обработчик данных всех ресурсов
     */
    private Totalizer totalizer;

    /**
     * Конструктор
     * @param totalizer общий обработчик данных всех ресурсов
     */
    public ExecutionServiceWorker(Totalizer totalizer) {
        this.totalizer = totalizer;
        PropertyConfigurator.configure("src/main/resources/log4j.xml");
    }

    /**
     * Запуск потоков обработки ресурсов
     * @param resources список ресурсов: URL или путь до файла
     */
    public void work(String[] resources) {
        ExecutorService pool = Executors.newFixedThreadPool(resources.length);
        ArrayList<Future> futures = new ArrayList<Future>();

        for (String resource: resources) {
            try {
                InputStream stream = ResourceReader.getStream(resource);
                final ResourceReader reader = new ResourceReader(
                    new Parser(new Scanner(stream)),
                    this.totalizer
                );

                futures.add(pool.submit(new Runnable() {
                    public void run() {
                        reader.read();
                    }
                }));

            } catch (ResourceNotFoundException e) {
                logger.error("Во время работы потоков, произошла ошибка с чтением ресурса", e);
            }
        }

        for (Future future: futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                logger.error("Прерывание на одном из потоков");
            } catch (ExecutionException e) {
                logger.error("Ошибка в работе одного из потоков");
            }
        }

    }
}
