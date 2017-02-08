package com.talipov;

import java.io.FileNotFoundException;

/**
 * Created by Марсель on 08.02.2017.
 */
public class ResourceThread extends Thread {

    private ResourceReader reader;

    public ResourceThread(ResourceReader reader) {
        this.reader = reader;
    }

    @Override
    public void run() {
        this.reader.read();
    }
}
