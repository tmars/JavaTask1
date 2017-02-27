package com.talipov;

import com.talipov.parser.AsyncParser;
import com.talipov.parser.Parser;
import com.talipov.parser.ScannerParser;
import com.talipov.totalizer.ReentrantLockTotalizer;
import com.talipov.totalizer.Totalizer;
import com.talipov.worker.ExecutionServiceWorker;
import com.talipov.worker.ResourcePoolWorker;
import com.talipov.worker.ResourceWorker;
import com.talipov.worker.SingleWorker;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.util.Scanner;

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

        args = new String[]{
//            "http://ws.test.last-man.org/static/1.txt",
//            "http://ws.test.last-man.org/static/2.txt",
            "src/main/resources/data/1.txt",
//            "src/main/resources/data/2.txt",
//            "src/main/resources/data/1.txt",
//            "src/main/resources/data/2.txt",
//            "src/main/resources/data/3.txt",
        };
        Class<? extends Parser> parserClass = AsyncParser.class;

        benchmark(args, new ResourcePoolWorker(new Totalizer()), parserClass);
        benchmark(args, new ResourcePoolWorker(new ReentrantLockTotalizer()), parserClass);
        benchmark(args, new ExecutionServiceWorker(new Totalizer()), parserClass);
        benchmark(args, new ExecutionServiceWorker(new ReentrantLockTotalizer()), parserClass);
        benchmark(args, new SingleWorker(new Totalizer()), parserClass);
        benchmark(args, new SingleWorker(new ReentrantLockTotalizer()), parserClass);
    }

    /**
     * Totalizer + ResourcePoolWorker 1910
     * Totalizer + ExecutionServiceWorker 3774
     * ReentrantLockTotalizer + ResourcePoolWorker 4155
     * ReentrantLockTotalizer + ExecutionServiceWorker 2964
     */
    public static void benchmark(String[] resources, ResourceWorker worker, Class<? extends Parser> parserClass) {
        long time = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            worker.work(resources, parserClass);
        }
        time = System.currentTimeMillis() - time;

        if (!worker.getTotalizer().isActive()) {
            System.out.println("Ошибка во время обработки ресурсов. Результат неизвестен.");
            return;
        }

        System.out.println("Worker=[" + worker.getClass().getSimpleName() + "] " +
                "Totalizer=[" + worker.getTotalizer().getClass().getSimpleName() + "] " +
                "Parser=[" + parserClass.getSimpleName() + "] " +
                "Время обработки = " + time + ", " +
                "Результат = " + worker.getTotalizer().getResult());
    }
}
