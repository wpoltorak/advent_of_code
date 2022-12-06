package com.adventofcode.calendar.impl;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.adventofcode.calendar.Puzzle;

public class Day6 implements Puzzle {

    @Override
    public String partOne(String input) {
        return "Sequence index: " + findSequenceIndex(4, input.toCharArray());
    }

    @Override
    public String partTwo(String input) {
        return "Sequence index: " + findSequenceIndex(14, input.toCharArray());
    }

    private int findSequenceIndex(int length, char[] ch) {
        int num = 0;
        for (int i = 0; i < ch.length - length - 1; i++) {
            if (length == countUniqueCharacters(new String(ch, i, length))) {
                num = i + length;
                break;
            }
        }
        return num;
    }

    private long countUniqueCharacters(String text) {
        return text.chars().mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).values().stream()
                .count();
    }
}
