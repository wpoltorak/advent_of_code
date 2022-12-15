package com.adventofcode.calendar.impl;

import com.adventofcode.calendar.Puzzle;

public class Day10 implements Puzzle {

    private static final int SIGNAL_STRENGTH_START = 20;
    private static final int SIGNAL_STRENGTH_INTERVAL = 40;
    private static final char LIT_PIXEL = '#';
    private static final char DARK_PIXEL = '.';

    @Override
    public String partOne(String input) {
        String[] lines = input.split(System.lineSeparator());
        int cycle = 0;
        int x = 1;
        int sum = 0;
        for (String line : lines) {
            String[] s = line.split(" ");
            sum += checkSignalStrength(++cycle, x);
            switch (s[0]) {
                case "addx":
                    sum += checkSignalStrength(++cycle, x);
                    x += Integer.valueOf(s[1]);
                    break;
            }
        }
        return "Sum of signals: " + sum;
    }

    private int checkSignalStrength(int cycle, int x) {
        return (cycle == SIGNAL_STRENGTH_START || ((cycle - SIGNAL_STRENGTH_START) % SIGNAL_STRENGTH_INTERVAL) == 0)
                ? cycle * x
                : 0;
    }

    @Override
    public String partTwo(String input) {
        String[] lines = input.split(System.lineSeparator());
        int cycle = 0;
        int x = 1;
        StringBuilder screen = new StringBuilder(System.lineSeparator());
        for (String line : lines) {
            String[] s = line.split(" ");
            drawPixel(screen, ++cycle, x);
            switch (s[0]) {
                case "addx":
                    drawPixel(screen, ++cycle, x);
                    x += Integer.valueOf(s[1]);
                    break;
            }
        }
        return "Message on screen: " + screen.toString();
    }

    private void drawPixel(StringBuilder screen, int cycle, int x) {
        int pos = (cycle % SIGNAL_STRENGTH_INTERVAL) - 1;
        screen.append(pos == x || pos == x - 1 || pos == x + 1 ? LIT_PIXEL : DARK_PIXEL);
        if (cycle % SIGNAL_STRENGTH_INTERVAL == 0) {
            screen.append(System.lineSeparator());
        }
    }
}