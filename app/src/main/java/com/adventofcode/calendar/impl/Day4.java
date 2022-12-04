package com.adventofcode.calendar.impl;

import com.adventofcode.calendar.Day;

public class Day4 implements Day {

    @Override
    public void run() {
        String out = readInput("/day4.txt");
        int num = 0;
        for (String s : out.split(System.lineSeparator())) {
            String[] l = s.split(",");
            Range r1 = Range.fromString(l[0]);
            Range r2 = Range.fromString(l[1]);
            num += overlaps(r1, r2);
        }
        System.out.println("No of overlapped pairs: " + num);
    }

    private int overlaps(Range r1, Range r2){
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
            String[] split = s.split("-");
            return new Range(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        }
    }
}
