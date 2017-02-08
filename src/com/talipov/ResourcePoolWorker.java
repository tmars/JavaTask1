package com.talipov;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Марсель on 08.02.2017.
 */
public class ResourcePoolWorker {

    private ArrayList<Thread> threads = new ArrayList<>();
    private Totalizer totalizer;

    public ResourcePoolWorker(Totalizer totalizer) {
        this.totalizer = totalizer;
    }

    public void work(String[] resources) {

        for (String resource: resources) {
            try {
                ResourceReader reader = new ResourceReader(resource, this.totalizer);
                Thread thread = new ResourceThread(reader);
                threads.add(thread);
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        for (Thread thread: threads) {
            thread.start();
        }

        for (Thread thread: threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Ошибка при работе с потоками");
            }
        }
    }


}
