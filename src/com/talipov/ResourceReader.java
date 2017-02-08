package com.talipov;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Марсель on 08.02.2017.
 */
public class ResourceReader {

    private Parser parser;
    private Totalizer totalizer;

    public ResourceReader(String resource, Totalizer totalizer) throws FileNotFoundException {
        InputStream stream = getStream(resource);
        if (stream == null) {
            throw new FileNotFoundException();
        }

        this.parser = new Parser(new Scanner(stream));
        this.totalizer = totalizer;
    }

    public void read() {
        Integer val = null;
        try {
            while ((val = parser.getNext()) != null && totalizer.isActive()) {
                totalizer.add(val);
            }
        } catch (ParserErrorException e) {
            System.out.println(e.getMessage());
            totalizer.setActive(false);
        }
    }

    private InputStream getStream(String path) {
        URL url;
        if (path.startsWith("http://") || path.startsWith("https://")) {
            try {
                url = new URL(path);
            } catch (MalformedURLException e) {
                System.out.println("Ошибка чтения ресурса по URL:" + path);
                return null;
            }
        } else {
            url = getClass().getResource(path);
            if (url == null) {
                System.out.println("Ошибка чтения файла ресурса:" + path);
                return null;
            }
        }

        try {
            return url.openStream();
        } catch (IOException e) {
            System.out.println("Ошибка при работе с ресурсом:" + path);
        }

        return null;
    }
}
