package com.adventofcode.calendar.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.adventofcode.calendar.Puzzle;

public class Day5 implements Puzzle {
    private static final String STACK_CRATE_REGEX = "(.{3}) ";
    private static final String STACK_NUMBER_SEPARATOR = "   ";
    private static final String INSTRUCTIONS_REGEX = "[A-Za-z]+ ";
    private static final String CONFIGURATION_AND_INSTRUCTIONS_SEPARATOR = System.lineSeparator()+System.lineSeparator();

    @Override
    public String partOne(String input) {
        String[] s = input.split(CONFIGURATION_AND_INSTRUCTIONS_SEPARATOR);
        
        String[] configuration = s[0].split(System.lineSeparator());
        String[] instructions = s[1].split(System.lineSeparator());

        Map<Integer, List<Integer>> stacks = createStacks(configuration);
        new CraneMoove9000().processMovement(stacks, instructions);
        return printCrates(stacks);
    }

    @Override
    public String partTwo(String input) {
        String[] s = input.split(CONFIGURATION_AND_INSTRUCTIONS_SEPARATOR);
        
        String[] configuration = s[0].split(System.lineSeparator());
        String[] instructions = s[1].split(System.lineSeparator());

        Map<Integer, List<Integer>> stacks = createStacks(configuration);
        new CraneMoove9001().processMovement(stacks, instructions);
        return printCrates(stacks);
    }

    private String printCrates(Map<Integer, List<Integer>> stacks) {
        String str = "";
        for (int i = 1; i <= stacks.size(); i++) {
            str += "" + ((char)stacks.get(i).get(stacks.get(i).size() - 1).intValue());
        }
        //  IntStream mapToInt = stacks.values().stream().mapToInt(s -> s.stream().mapToInt(Integer::valueOf).reduce((first, second) -> second)
        return "Top crates: " + str;
    }

    private Map<Integer, List<Integer>> createStacks(String[] config){
        Map<Integer, List<Integer>> stacks = createEmptyStacks(config[config.length - 1].trim().split(STACK_NUMBER_SEPARATOR));
        fillStacksWithCrates(stacks, config);
        
        return stacks; 
    }

    private void fillStacksWithCrates(Map<Integer, List<Integer>> stacks, String[] config) {
        String regex = createStacksRegex(stacks.size());
        Pattern p = Pattern.compile(regex);

        for (int i = config.length - 2; i >= 0; i--) {
            addLayerOfCrates(stacks, config[i], p);
        }
    }

    private void addLayerOfCrates(Map<Integer, List<Integer>> stacks, String layerConfig, Pattern p) {
        Matcher m = p.matcher(layerConfig);
        m.find();
        for (int stackNumber = 1; stackNumber <= m.groupCount(); stackNumber++) {
            addCrateToStackIfExists(stacks.get(stackNumber), m.group(stackNumber));
        }
    }

    private void addCrateToStackIfExists(List<Integer> stack, String crate) {
        if (crate.isBlank()){
            return;
        }
        stack.add(Integer.valueOf(crate.charAt(1)));
    }

    private Map<Integer, List<Integer>> createEmptyStacks( String[] numberOfStacks) {
        Map<Integer, List<Integer>> stacks = new TreeMap<>();
        for (String label : numberOfStacks) {
            stacks.put(Integer.valueOf(label), new ArrayList<>());
        }
        return stacks;
    }

    private String createStacksRegex(int length){
        return STACK_CRATE_REGEX.repeat(length).trim();
    }
    
    private interface Crane {

        default Map<Integer, List<Integer>> processMovement(Map<Integer, List<Integer>> stacks, String[] instructions) {
            for (String instruction : instructions) {
                String[] instructionNumbers = retainNumbers(instruction);
                int numberOfCrates = Integer.valueOf(instructionNumbers[0]);
                int fromStackNumber = Integer.valueOf(instructionNumbers[1]);
                int toStackNumber = Integer.valueOf(instructionNumbers[2]);
                move(stacks.get(fromStackNumber), stacks.get(toStackNumber), numberOfCrates);
            }
            return stacks;
        }

        private String[] retainNumbers(String instruction) {
            return instruction.replaceAll(INSTRUCTIONS_REGEX, "").split(" ");
        }

        void move(List<Integer> srcCrates, List<Integer> destCrates, int numberOfCrates);
    }

    private class CraneMoove9000 implements Crane {

        @Override
        public void move(List<Integer> srcCrates, List<Integer> destCrates, int numberOfCrates) {
            for (int i = 0; i < numberOfCrates; i++) {
                destCrates.add(srcCrates.remove(srcCrates.size() - 1));
            }
        }
    }

    private class CraneMoove9001 implements Crane {

        @Override
        public void move(List<Integer> srcCrates, List<Integer> destCrates, int numberOfCrates) {
            List<Integer> temp = srcCrates.subList(srcCrates.size() - numberOfCrates, srcCrates.size());
            destCrates.addAll(temp);
            temp.clear();
        }
    }
}
