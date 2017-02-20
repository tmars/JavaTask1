package com.talipov.worker;

import com.talipov.Parser;
import com.talipov.ResourceNotFoundException;
import com.talipov.ResourceReader;
import com.talipov.totalizer.TotalizerInterface;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Марсель on 08.02.2017.
 * Пул потоков обработки всех ресурсов
 */
public class ResourcePoolWorker extends ResourceWorker {

    /**
     * Логгер
     */
    private static final Logger logger = Logger.getLogger(ResourcePoolWorker.class);

    /**
     * Конструктор
     * @param totalizer общий обработчик данных всех ресурсов
     */
    public ResourcePoolWorker(TotalizerInterface totalizer) {
        super(totalizer);
        PropertyConfigurator.configure("src/main/resources/log4j.xml");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void work(String[] resources) {
        ArrayList<Thread> threads = new ArrayList<Thread>();

        for (String resource: resources) {
            try {
                InputStream stream = ResourceReader.getStream(resource);
                final ResourceReader reader = new ResourceReader(
                    new Parser(new Scanner(stream)),
                    this.totalizer
                );

                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        reader.read();
                    }
                });
                threads.add(thread);
            } catch (ResourceNotFoundException e) {
                logger.error("Во время работы потоков, произошла ошибка с чтением ресурса", e);
                return;
            }
        }

        logger.info("Запуск потоков обработки ресурсов. Кол-во: " + threads.size());
        for (Thread thread: threads) {
            thread.start();
        }

        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.error("Ошибка при работе с потоками обработки ресурсов");
            }
        }
    }
}
