package com.talipov.worker;

import com.talipov.ResourceNotFoundException;
import com.talipov.ResourceReader;
import com.talipov.parser.Parser;
import com.talipov.totalizer.TotalizerInterface;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Марсель on 19.02.2017.
 */
public class ExecutionServiceWorker extends ResourceWorker {
    /**
     * Логгер
     */
    private static final Logger logger = Logger.getLogger(ExecutionServiceWorker.class);

    /**
     * Конструктор
     * @param totalizer общий обработчик данных всех ресурсов
     */
    public ExecutionServiceWorker(TotalizerInterface totalizer) {
        super(totalizer);
        PropertyConfigurator.configure("src/main/resources/log4j.xml");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void work(String[] resources, Class<? extends Parser> parserClass) {
        ExecutorService pool = Executors.newFixedThreadPool(resources.length);
        ArrayList<Future> futures = new ArrayList<Future>();

        for (String resource: resources) {
            try {
                final ResourceReader reader = new ResourceReader(
                    createParser(parserClass, resource),
                    this.totalizer
                );

                futures.add(pool.submit(new Runnable() {
                    public void run() {
                        reader.read();
                    }
                }));

            } catch (ResourceNotFoundException e) {
                logger.error("Ошибка чтения ресурса", e);
                return;
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

        pool.shutdown();

    }
}
