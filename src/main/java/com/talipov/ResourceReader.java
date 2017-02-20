package com.talipov;

import com.talipov.totalizer.TotalizerInterface;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Марсель on 08.02.2017.
 * Ридер данных из ресурса с помощью парсера
 */
public class ResourceReader {

    /**
     * Логгер
     */
    private static final Logger logger = Logger.getLogger(ResourceReader.class);

    /**
     * Парсер данных из ресурсов
     */
    private Parser parser;

    /**
     * Общий обработчик данных всех ресурсов
     */
    private TotalizerInterface totalizer;

    /**
     * Конструктор
     * @param parser парсер, который читает из ресурса
     * @param totalizer общий обработчик данных всех ресурсов
     * @throws FileNotFoundException выкидывается в случае если ресурс не найден
     */
    public ResourceReader(Parser parser, TotalizerInterface totalizer) {
        this.parser = parser;
        this.totalizer = totalizer;
        PropertyConfigurator.configure("src/main/resources/log4j.xml");
    }

    /**
     * Последовательное чтение данных из ресурса по средствам парсера.
     * Если попались некорректные данные, то общий обработчик помечается неактивным,
     * для того чтобы другие потоки обработки остановились
     */
    public void read() {
        Integer val = null;
        try {
            while ((val = parser.getNext()) != null && totalizer.isActive()) {
                totalizer.add(val);
            }
        } catch (ParserErrorException e) {
            logger.error("Ошибка парсинга", e);
            totalizer.setActive(false);
        }
    }

    /**
     * Возвращает поток данных соответствующего ресурса
     * @param path ресурс: URL или путь до файла
     * @return потом данных для чтения
     */
    public static InputStream getStream(String path) throws ResourceNotFoundException {
        InputStream stream = null;

        if (path.startsWith("http://") || path.startsWith("https://")) {
            try {
                URL url = new URL(path);
                stream = url.openStream();
            } catch (MalformedURLException e) {
                logger.error("Ошибка чтения ресурса по URL: " + path);
            } catch (IOException e) {
                logger.error("Ошибка при работе с ресурсом:" + path);
            }
        } else {
            try {
                stream = new FileInputStream(path);
            } catch (FileNotFoundException e) {
                logger.error("Ошибка чтения файла ресурса: " + path);
            }
        }

        if (stream == null) {
            throw new ResourceNotFoundException();
        }
        return stream;
    }


}
