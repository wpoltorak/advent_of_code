package com.adventofcode.calendar.impl;

import java.util.Map;
import com.adventofcode.calendar.Day;
import static java.util.Map.entry;

public class Day2 implements Day {

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
        public void run() {

                String out = readInput("/day2.txt");
                int score1 = 0;
                int score2 = 0;

                for (String game : out.split(System.lineSeparator())) {
                        score1 += SCOREBOARD.get(game);
                        score2 += SCOREBOARD.get(STRATEGIES.get(game));
                }

                System.out.println("Score 1: " + score1);
                System.out.println("Score 2: " + score2);
        }
}
