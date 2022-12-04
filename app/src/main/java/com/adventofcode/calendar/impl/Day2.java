package com.adventofcode.calendar.impl;

import java.util.Map;
import com.adventofcode.calendar.Puzzle;
import static java.util.Map.entry;

public class Day2 implements Puzzle {

        private static final Map<String, Integer> SCOREBOARD = Map.ofEntries(
                        entry("A X", 3 + 1),
                        entry("A Y", 6 + 2),
                        entry("A Z", 0 + 3),
                        entry("B X", 0 + 1),
                        entry("B Y", 3 + 2),
                        entry("B Z", 6 + 3),
                        entry("C X", 6 + 1),
                        entry("C Y", 0 + 2),
                        entry("C Z", 3 + 3));

        private static final Map<String, String> STRATEGIES = Map.ofEntries(
                        entry("A X", "A Z"),
                        entry("A Y", "A X"),
                        entry("A Z", "A Y"),
                        entry("B X", "B X"),
                        entry("B Y", "B Y"),
                        entry("B Z", "B Z"),
                        entry("C X", "C Y"),
                        entry("C Y", "C Z"),
                        entry("C Z", "C X"));

        @Override
        public String partOne(String input) {
                int score = 0;
                for (String game : input.split(System.lineSeparator())) {
                        score += SCOREBOARD.get(game);
                }
                return("Score 1: " + score);
        }

        @Override
        public String partTwo(String input) {
                int score = 0;
                for (String game : input.split(System.lineSeparator())) {
                        score += SCOREBOARD.get(STRATEGIES.get(game));
                }
                return "Score 2: " + score;
        }
}
