package com.talipov.worker;

import com.talipov.Parser;
import com.talipov.ResourceNotFoundException;
import com.talipov.ResourceReader;
import com.talipov.totalizer.TotalizerInterface;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Created by Марсель on 19.02.2017.
 * Обработка ресурсов в один поток
 */
public class SingleWorker extends ResourceWorker {

    /**
     * Логгер
     */
    private static final Logger logger = Logger.getLogger(SingleWorker.class);

    /**
     * Конструктор
     * @param totalizer общий обработчик данных всех ресурсов
     */
    public SingleWorker(TotalizerInterface totalizer) {
        super(totalizer);
        PropertyConfigurator.configure("src/main/resources/log4j.xml");
    }

    /**
     * @inheritDoc
     */
    @Override
    public void work(String[] resources) {
        for (String resource: resources) {
            try {
                InputStream stream = ResourceReader.getStream(resource);
                ResourceReader reader = new ResourceReader(
                        new Parser(new Scanner(stream)),
                        totalizer
                );
                reader.read();
            } catch (ResourceNotFoundException e) {
                logger.error("Ошибка чтения ресурса", e);
                break;
            }
        }
    }
}
