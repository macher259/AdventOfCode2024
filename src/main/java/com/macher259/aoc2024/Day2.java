package com.macher259.aoc2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day2 {
    private final List<List<Integer>> reports = new ArrayList<>();

    public Day2(String data) {
        for (var line : data.split("\\n")) {
            var arr = Arrays.stream(line.split(" "))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            reports.add(arr);
        }
    }

    private boolean isSafe(List<Integer> report) {
        if (report.size() < 2) {
            return true;
        }

        var isIncreasing = report.get(0) < report.get(1);
        if (Objects.equals(report.get(0), report.get(1))) {
            return false;
        }
        for (int i = 1; i < report.size(); i++) {
            if (isIncreasing && report.get(i) <= report.get(i - 1)) {
                return false;
            }
            if (!isIncreasing && report.get(i) >= report.get(i - 1)) {
                return false;
            }
            if (Math.abs(report.get(i) - report.get(i - 1)) < 1 || Math.abs(report.get(i) - report.get(i - 1)) > 3) {
                return false;
            }
        }
        return true;
    }

    public String part1() {
        long ans = reports.stream().filter(this::isSafe).count();
        return String.valueOf(ans);
    }

    public String part2() {
        long ans = 0;
        for (var report : reports) {
            for (int i = 0; i < report.size(); i++) {
                var copy = new ArrayList<>(report);
                copy.remove(i);
                if (isSafe(copy)) {
                    ans += 1;
                    break;
                }
            }
        }
        return String.valueOf(ans);
    }
}
