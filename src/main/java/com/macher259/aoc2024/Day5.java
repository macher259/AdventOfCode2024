package com.macher259.aoc2024;


import java.util.*;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.util.Map.entry;
import static java.util.stream.Collectors.*;
import static java.util.stream.Gatherers.windowSliding;

public class Day5 {
    private final List<List<Integer>> updates;
    private final Map<Integer, Set<Integer>> pages;
    private final Comparator<Integer> comp;

    public Day5(String data) {
        var all = data.split("\\n\\n");
        updates = Arrays.stream(all[1].split("\\n"))
                .map(update -> Arrays.stream(update.split(","))
                        .map(Integer::parseInt)
                        .toList())
                .toList();

        pages = Pattern.compile("(\\d+)\\|(\\d+)").matcher(String.join("", all[0].split("\r\n"))).results()
                .map(m -> entry(parseInt(m.group(1)), parseInt(m.group(2))))
                .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toSet())));

        comp = (a, b) -> pages.getOrDefault(a, Set.of()).contains(b) ? 1 : -1;
    }

    public String part1() {
        return String.valueOf(
                updates.stream()
                        .filter(update -> update.stream()
                                .gather(windowSliding(2))
                                .allMatch(window -> pages.get(window.getFirst()).contains(window.getLast())))
                        .mapToInt(line -> line.get(line.size() / 2))
                        .sum());

    }

    public String part2() {
        return String.valueOf(
                updates.stream()
                        .filter(pages -> !pages.stream()
                                .gather(windowSliding(2))
                                .allMatch(window -> this.pages.get(window.getFirst()).contains(window.getLast())))
                        .map(list -> list.stream().sorted(comp).toList())
                        .mapToInt(line -> line.get((line.size() - 1) / 2))
                        .sum());
    }
}
