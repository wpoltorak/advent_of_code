package com.adventofcode.calendar.impl;

import java.util.Map;
import java.util.stream.Collectors;
import com.adventofcode.calendar.Day;

public class Day3 implements Day {

    private static final int ASCII_UPPERACE_OFFSET = 38;
    private static final int ASCII_LOWERACE_OFFSET = 96;

    private final int calculatePriorityNumber(int character) {
        return character <= ASCII_LOWERACE_OFFSET ? character - ASCII_UPPERACE_OFFSET
                : character - ASCII_LOWERACE_OFFSET;
    }

    @Override
    public void run() {
        String out = readInput("/day3.txt");

        puzzle1(out);
        puzzle2(out);
    }

    private void puzzle1(String input) {
        int sumOfPriorities = 0;

        for (String rucksack : input.split(System.lineSeparator())) {
            int middle = rucksack.length() / 2;
            String compartment1 = rucksack.substring(0, middle);
            String compartment2 = rucksack.substring(middle);

            sumOfPriorities += priorityOfItemFromAll(compartment1, compartment2);
        }
        System.out.println("Sum of priorities: " + sumOfPriorities);
    }

    private void puzzle2(String out) {
        int b = 0;
        String[] rucksacks = out.split(System.lineSeparator());
        if (rucksacks.length % 3 > 0) {
            return;
        }

        for (int i = 0; i < rucksacks.length - 2; i += 3) {
            b += priorityOfItemFromAll(rucksacks[i], rucksacks[i + 1], rucksacks[i + 2]);
        }
        System.out.println("Sum of group priorities: " + b);
    }

    private int priorityOfItemFromAll(String first, String... rest) {
        Map<Character, Integer> map = buildPriorities(first);
        for (String pot : rest) {
            map.keySet().retainAll(pot.chars().mapToObj(e -> (char) e).collect(Collectors.toSet()));
        }
        return map.values().stream().mapToInt(Integer::intValue).sum();
    }

    private Map<Character, Integer> buildPriorities(String items) {
        return items.chars().mapToObj(i -> (char) i)
                .collect(Collectors.toMap(c -> c, c -> calculatePriorityNumber(c), (c1, c2) -> c1));
    }
}