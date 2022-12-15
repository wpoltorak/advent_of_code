package com.adventofcode.calendar.impl;

import com.adventofcode.calendar.Puzzle;

public class Day8 implements Puzzle {

    @Override
    public String partOne(String input) {
        char[][] heights = readHeights(input.split(System.lineSeparator()));

        int hLength = heights[0].length;
        int vLength = heights.length;

        boolean[][] visibility = new boolean[vLength][hLength];

        for (int v = 1; v < vLength - 1; v++) {
            int maxWest = heights[v][0];
            int maxEast = heights[v][hLength - 1];
            for (int h = 1; h < hLength - 1; h++) {
                maxWest = setVisibility(visibility, v, h, heights[v][h], maxWest);
                maxEast = setVisibility(visibility, v, hLength - h - 1, heights[v][hLength - h - 1], maxEast);
            }
        }
        for (int h = 1; h < hLength - 1; h++) {
            int maxNorth = heights[0][h];
            int maxSouth = heights[vLength - 1][h];
            for (int v = 1; v < vLength - 1; v++) {
                maxNorth = setVisibility(visibility, v, h, heights[v][h], maxNorth);
                maxSouth = setVisibility(visibility, vLength - v - 1, h, heights[vLength - v - 1][h], maxSouth);
            }
        }
        return "Visible trees: " + sumVisibleTrees(visibility);
    }

    private int setVisibility(boolean[][] visibility, int v, int h, int current, int max) {
        visibility[v][h] = visibility[v][h] || current > max;
        return Math.max(current, max);
    }

    @Override
    public String partTwo(String input) {
        char[][] heights = readHeights(input.split(System.lineSeparator()));
        int maxScore = 0;
        for (int v = 0; v < heights.length; v++) {
            for (int h = 0; h < heights[0].length; h++) {
                int scenicScore = lookHorizontal(h, heights[v]) * lookVertical(v, h, heights);
                maxScore = Math.max(maxScore, scenicScore);

            }
        }
        return "Max view: " + maxScore;
    }

    private char[][] readHeights(String[] lines) {
        char[][] heights = new char[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            heights[i] = lines[i].toCharArray();
        }
        return heights;
    }

    private int sumVisibleTrees(boolean[][] visibility) {
        int sum = 2 * visibility[0].length + 2 * (visibility.length - 2);

        for (int i = 1; i < visibility.length - 1; i++) {
            for (int j = 1; j < visibility[i].length - 1; j++) {
                sum += visibility[i][j] ? 1 : 0;
            }
        }
        return sum;
    }

    int lookHorizontal(int current, char[] row) {
        int west = 0;
        int east = 0;
        boolean visible = true;
        for (int i = current; i < row.length && visible; i++) {
            int next = i + 1;
            visible = next < row.length && row[next] < row[current];
            east += next < row.length ? 1 : 0;
        }
        visible = true;
        for (int j = current; j >= 0 && visible; j--) {
            int prev = j - 1;
            visible = prev >= 0 && row[prev] < row[current];
            west += prev >= 0 ? 1 : 0;
        }
        return west * east;
    }

    int lookVertical(int current, int hIndex, char[][] cols) {
        int south = 0;
        int north = 0;
        boolean visible = true;
        for (int i = current; i < cols.length && visible; i++) {
            int next = i + 1;
            visible = next < cols.length && cols[next][hIndex] < cols[current][hIndex];
            south += next < cols.length ? 1 : 0;
        }
        visible = true;
        for (int j = current; j >= 0 && visible; j--) {
            int prev = j - 1;
            visible = prev >= 0 && cols[prev][hIndex] < cols[current][hIndex];
            north += prev >= 0 ? 1 : 0;
        }
        return south * north;
    }
}