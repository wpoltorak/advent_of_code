package com.adventofcode.calendar;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface Day {

    void run();


    default String readInput(String name) {
        try {

            Path path = Paths.get(getClass().getResource(name).toURI());
            return Files.readString(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
