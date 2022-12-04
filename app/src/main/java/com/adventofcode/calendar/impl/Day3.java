package com.adventofcode.calendar.impl;

import java.util.HashMap;
import java.util.Map;

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

            sumOfPriorities += priorityOfItemFromBothCompartments(compartment1, compartment2);
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
            String g1 = rucksacks[i];
            String g2 = rucksacks[i + 1];
            String g3 = rucksacks[i + 2];
            for (char c : g1.toCharArray()) {
                if (g2.indexOf(c) >= 0 && g3.indexOf(c) >= 0) {
                    int val = calculatePriorityNumber(c);
                    b += val;
                    break;
                }
            }
        }
        System.out.println("Sum of group priorities: " + b);
    }

    private int priorityOfItemFromBothCompartments(String compartment1, String compartment2) {
        Map<Character, Integer> map = buildPriorities(compartment1);
        for (char c : compartment2.toCharArray()) {
            if (map.containsKey(c)) {
                return map.get(c);
            }
        }
        return 0;
    }

    private Map<Character, Integer> buildPriorities(String items) {
        Map<Character, Integer> map = new HashMap<>();

        for (char item : items.toCharArray()) {
            map.put(item, calculatePriorityNumber(item));
        }
        return map;
    }
}