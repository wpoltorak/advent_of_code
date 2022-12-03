package com.adventofcode.calendar.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.ToIntFunction;

import com.adventofcode.calendar.Day;

public class Day1 implements Day {

    @Override
    public void run() {
        String out = readInput("/day1.txt");
        String[] caloryEntries = out.split(System.lineSeparator() + System.lineSeparator());
        ToIntFunction<? super String> summedCaloriesFunction = s -> Arrays.stream(s.split(System.lineSeparator()))
                .mapToInt(Integer::parseInt).sum();

        int max = Arrays.stream(caloryEntries)
                .mapToInt(summedCaloriesFunction).max()
                .getAsInt();

        System.out.println("Most carried calories " + max);

        max = Arrays.stream(caloryEntries)
                .mapToInt(summedCaloriesFunction).boxed()
                .sorted(Comparator.reverseOrder()).limit(3).mapToInt(Integer::intValue)
                .sum();

        System.out.println("Sum of top 3 most carried calories " + max);
    }
}
