package com.talipov;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Totalizer totalizer = new Totalizer();

        args = new String[]{
                "http://ws.test.last-man.org/static/1.txt",
                "http://ws.test.last-man.org/static/2.txt",
                "/data/1.txt",
                "/data/2.txt",
                "http://ws.test.last-man.org/static/1.txt",
                "http://ws.test.last-man.org/static/2.txt",
                "/data/1.txt",
                "/data/2.txt",
//                "/data/3.txt",
        };
//        singleThread(args, totalizer);
        multiThread(args, totalizer);

        if (totalizer.isActive()) {
            System.out.println("Итог = " + totalizer.getResult());
        } else {
            System.out.println("Ошибка во время обработки ресурсов. Результат неизвестен.");
        }
    }

    private static void singleThread(String[] resources, Totalizer totalizer) {
        for (String resource: resources) {
            try {
                ResourceReader reader = new ResourceReader(resource, totalizer);
                reader.read();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                break;
            }
        }
    }

    private static void multiThread(String[] resources, Totalizer totalizer) {
        ResourcePoolWorker worker = new ResourcePoolWorker(totalizer);
        worker.work(resources);
    }
}
