package com.talipov;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Totalizer totalizer = new Totalizer();

        for (String resource: args) {
            try {
                ResourceReader reader = new ResourceReader(resource, totalizer);
                reader.read();
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
