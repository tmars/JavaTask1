package com.talipov;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Main {

    /**
     * Логгер
     */
    private static final Logger logger = Logger.getLogger(Main.class);

    /**
     * Инициализация логгера
     */
    static {
        PropertyConfigurator.configure("src/main/resources/log4j.xml");
    }

    public static void main(String[] args) {
        Totalizer totalizer = new Totalizer();

        args = new String[]{
                "http://ws.test.last-man.org/static/1.txt",
                "http://ws.test.last-man.org/static/2.txt",
                "src/main/resources/data/1.txt",
                "src/main/resources/data/2.txt",
                "http://ws.test.last-man.org/static/1.txt",
                "http://ws.test.last-man.org/static/2.txt",
                "src/main/resources/data/1.txt",
                "src/main/resources/data/2.txt",
                "src/main/resources/data/3.txt",
        };
//        singleThread(args, totalizer);
        multiThread(args, totalizer);

        if (totalizer.isActive()) {
            logger.trace("Итог = " + totalizer.getResult());
        } else {
            logger.trace("Ошибка во время обработки ресурсов. Результат неизвестен.");
        }
    }

    /**
     * Обработка ресурсов в один поток
     * @param resources список ресурсов
     * @param totalizer общий обработчик данных всех ресурсов
     */
    private static void singleThread(String[] resources, Totalizer totalizer) {
        for (String resource: resources) {
            try {
                ResourceReader reader = new ResourceReader(resource, totalizer);
                reader.read();
            } catch (ResourceNotFoundException e) {
                logger.error("Ошибка чтения ресурса", e);
                break;
            }
        }
    }

    /**
     * Обработка ресурсов в многопоточном режиме
     * @param resources список ресурсов
     * @param totalizer общий обработчик данных всех ресурсов
     */
    private static void multiThread(String[] resources, Totalizer totalizer) {
        ResourcePoolWorker worker = new ResourcePoolWorker(totalizer);
        worker.work(resources);
    }
}
