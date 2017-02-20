package com.talipov.parser;

import com.talipov.ParserErrorException;
import com.talipov.ResourceNotFoundException;
import com.talipov.worker.ResourcePoolWorker;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Марсель on 07.02.2017.
 * Парсер целых чисел из данных ресурса
 */
public class ScannerParser extends Parser {

    /**
     * Входные данные
     */
    private Scanner input;

    /**
     * Конструктов
     * @param filename входные данные
     */
    public ScannerParser(String filename) throws ResourceNotFoundException {
        super(filename);
        input = new Scanner(getStream(filename));
    }

    /**
     * Возвращает поток данных соответствующего ресурса
     * @param path ресурс: URL или путь до файла
     * @return потом данных для чтения
     */
    private InputStream getStream(String path) throws ResourceNotFoundException {
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

    /**
     * Возвращает следующее целое число если возможно,
     * если данные не корректны выбрасывает исключение ParserErrorException
     * @return следующее целове число из выходных данных
     * @throws ParserErrorException выбрасывается в случае не корректных данных
     */
    public Integer getNext() throws ParserErrorException {
        if (input.hasNext()) {
            if (input.hasNextInt()) {
                return input.nextInt();
            } else {
                String s = input.next();
                input.close();

                throw new ParserErrorException(s);
            }
        } else {
            input.close();
        }

        return null;
    }
}
