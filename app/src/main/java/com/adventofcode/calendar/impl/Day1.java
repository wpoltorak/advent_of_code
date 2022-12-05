package com.adventofcode.calendar.impl;

import java.util.Arrays;
import java.util.Comparator;

import com.adventofcode.calendar.Puzzle;

public class Day1 implements Puzzle {

        private static final String SEPARATOR = System.lineSeparator() + System.lineSeparator();
        private static final int MAX_SIZE = 3;

        @Override
        public String partOne(String input) {
                int max = Arrays.stream(input.split(SEPARATOR))
                                .mapToInt(s -> Arrays
                                                .stream(s.split(System.lineSeparator()))
                                                .mapToInt(Integer::parseInt).sum())
                                .max()
                                .getAsInt();

                return "Most carried calories " + max;
        }

        @Override
        public String partTwo(String input) {
                int max = Arrays.stream(input.split(SEPARATOR))
                                .mapToInt(s -> Arrays
                                                .stream(s.split(System.lineSeparator()))
                                                .mapToInt(Integer::parseInt).sum())
                                .boxed()
                                .sorted(Comparator.reverseOrder()).limit(MAX_SIZE).mapToInt(Integer::intValue)
                                .sum();

                return "Sum of top 3 most carried calories " + max;
        }
}
