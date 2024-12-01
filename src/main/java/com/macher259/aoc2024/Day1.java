package com.macher259.aoc2024;

import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day1 {
    private final ArrayList<Integer> firstList = new ArrayList<>();
    private final ArrayList<Integer> secondList = new ArrayList<>();

    public Day1(String data) {
        data.lines().map(line -> line.split("\\s+")).forEach(arr -> {
            firstList.add(Integer.parseInt(arr[0]));
            secondList.add(Integer.parseInt(arr[1]));
        });

        firstList.sort(Integer::compareTo);
        secondList.sort(Integer::compareTo);
    }

    public String part1() {
        int ans = 0;
        for (int i = 0; i < firstList.size(); ++i) {
            ans += Math.abs(firstList.get(i) - secondList.get(i));
        }

        return String.valueOf(ans);
    }

    public String part2() {
        Map<Integer, Long> frequencyMap = secondList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        long similarityScore = 0;
        for (var location : firstList) {
            similarityScore += location * frequencyMap.getOrDefault(location, 0L);
        }

        return String.valueOf(similarityScore);
    }
}
