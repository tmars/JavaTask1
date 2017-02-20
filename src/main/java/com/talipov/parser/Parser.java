package com.talipov.parser;

import com.talipov.ParserErrorException;
import com.talipov.worker.ResourcePoolWorker;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Scanner;

/**
 * Created by Марсель on 07.02.2017.
 * Парсер целых чисел из данных ресурса
 */
public abstract class Parser {

    /**
     * Логгер
     */
    protected static final Logger logger = Logger.getLogger(ResourcePoolWorker.class);

    /**
     * Входные данные
     */
    protected String filename;

    /**
     * Конструктов
     * @param filename входные данные
     */
    public Parser(String filename) {
        this.filename = filename;
        PropertyConfigurator.configure("src/main/resources/log4j.xml");
    }

    /**
     * Возвращает следующее целое число если возможно,
     * если данные не корректны выбрасывает исключение ParserErrorException
     * @return следующее целове число из выходных данных
     * @throws ParserErrorException выбрасывается в случае не корректных данных
     */
    public abstract Integer getNext() throws ParserErrorException;
}
