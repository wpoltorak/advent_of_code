package com.adventofcode.calendar;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface Puzzle {

    default void run(){
        String name = getClass().getSimpleName();
        System.out.println("=== " + name +" ===");
        String out = readInput("/" + name.toLowerCase() + ".txt");
        System.out.println(partOne(out));
        System.out.println(partTwo(out));
    }

    String partOne(String input);

    String partTwo(String input);

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
