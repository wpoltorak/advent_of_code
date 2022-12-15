package com.adventofcode.calendar.impl;

import java.awt.Point;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

import com.adventofcode.calendar.Puzzle;

public class Day09 implements Puzzle {

    @Override
    public String partOne(String input) {
        String[] lines = input.split(System.lineSeparator());
        Rope rope = new Rope(1);
        for (int i = 0; i < lines.length; i++) {
            String[] commands = lines[i].split(" ");
            for (int j = 0; j < Integer.valueOf(commands[1]); j++) {
                switch (commands[0]) {
                    case "U":
                    case "D":
                        rope.moveVertical(commands[0].equals("U") ? 1 : -1);
                        break;
                    case "R":
                    case "L":
                        rope.moveHorizontal(commands[0].equals("R") ? 1 : -1);
                        break;
                }
            }
        }
        return "Sum of positions: " + rope.entries.size();
    }

    @Override
    public String partTwo(String input) {
        String[] lines = input.split(System.lineSeparator());
        Rope rope = new Rope(9);
        for (int i = 0; i < lines.length; i++) {
            String[] commands = lines[i].split(" ");
            for (int j = 0; j < Integer.valueOf(commands[1]); j++) {
                switch (commands[0]) {
                    case "U":
                        rope.moveVertical(1);
                        break;
                    case "D":
                        rope.moveVertical(-1);
                        break;
                    case "L":
                        rope.moveHorizontal(-1);
                        break;
                    case "R":
                        rope.moveHorizontal(1);
                        break;
                }
            }
        }
        rope.print();
        return "Sum of positions: " + rope.entries.size();
    }

    class Rope {
        Point head = new Point();
        LinkedList<Point> tail = new LinkedList<>();
        Set<Point> entries = new HashSet<Point>();

        public Rope(int tailSize) {
            for (int i = 0; i < tailSize; i++) {
                tail.add(new Point());
            }
            entries.add(new Point());
        }

        void moveVertical(int y) {
            head.translate(0, y);
            moveTail();
        }

        void moveHorizontal(int x) {
            head.translate(x, 0);
            moveTail();
        }

        private void moveTail() {
            Point previous = head;
            for (Iterator<Point> it = tail.iterator(); it.hasNext();) {
                Point next = it.next();
                if (Math.abs(previous.y - next.y) > 1) {
                    next.move(previous.x, next.y + (previous.y > next.y ? 1 : -1));
                    if (!it.hasNext()) {
                        entries.add(new Point(next.x, next.y));
                    }
                }
                if (Math.abs(previous.x - next.x) > 1) {
                    next.move(next.x + (previous.x > next.x ? 1 : -1), previous.y);
                    if (!it.hasNext()) {
                        entries.add(new Point(next.x, next.y));
                    }
                }
                previous = next;
            }
        }

        void print() {
            String[][] tab = new String[40][40];
            int size = 20;
            int maxx = 0;
            int minx = 0;
            int maxy = 0;
            int miny = 0;
            for (Iterator<Point> it = entries.iterator(); it.hasNext();) {
                Point p = it.next();
                maxx = Math.max(maxx, p.x);
                maxy = Math.max(maxy, p.y);
                minx = Math.min(minx, p.x);
                miny = Math.min(miny, p.y);
            }
            System.out.println("Maxx: " + maxx + ", min x: " + minx + ", maxy: " + maxy + ", miny: " + miny);
        }
    }
}