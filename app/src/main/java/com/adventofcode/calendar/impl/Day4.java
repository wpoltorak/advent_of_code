package com.adventofcode.calendar.impl;

import com.adventofcode.calendar.Puzzle;

public class Day4 implements Puzzle {
    private static final String COMMA_SEPARATOR = ",";
    private static final String DASH_SEPARATOR = "-";

    @Override
    public String partOne(String input) {
        int num = 0;
        for (String s : input.split(System.lineSeparator())) {
            String[] l = s.split(COMMA_SEPARATOR);
            Range r1 = Range.fromString(l[0]);
            Range r2 = Range.fromString(l[1]);
            num += countIfContains(r1, r2);
        }
        return "No of contained pairs: " + num;
    }

    @Override
    public String partTwo(String input) {
        int num = 0;
        for (String s : input.split(System.lineSeparator())) {
            String[] l = s.split(COMMA_SEPARATOR);
            Range r1 = Range.fromString(l[0]);
            Range r2 = Range.fromString(l[1]);
            num += countIfOverlaps(r1, r2);
        }
        return "No of overlapped pairs: " + num;
    }

    private int countIfContains(Range r1, Range r2){
        return r1.contains(r2) || r2.contains(r1) ? 1 : 0;
    } 

    private int countIfOverlaps(Range r1, Range r2){
        return r1.overlaps(r2) || r2.overlaps(r1) ? 1 : 0;
    }

    private static class Range {
        private int low;
        private int high;

        private Range(int low, int high) {
            this.low = low;
            this.high = high;
        }

        public boolean overlaps(Range r) {
            return contains(r) || intersects(r);
        }

        private boolean intersects(Range r) {
            return low <= r.low && high >= r.low && high < r.high;
        }

        private boolean contains(Range r) {
            return low <= r.low & high >= r.high;
        }

        public static final Range fromString(String s) {
            String[] split = s.split(DASH_SEPARATOR);
            return new Range(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }
    }
}
