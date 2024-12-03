package com.macher259.aoc2024;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

record Mul(long x, long y) {
}

public class Day3 {
    private final List<Mul> instructions = new ArrayList<>();
    private final List<Mul> enabledInstructions = new ArrayList<>();

    public Day3(String data) {
        String pattern = "mul\\((\\d+),(\\d+)\\)|do\\(\\)|don't\\(\\)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(data);

        boolean enabled = true;

        while (matcher.find()) {
            String m = matcher.group();
            switch (m) {
                case "do()":
                    enabled = true;
                    break;
                case "don't()":
                    enabled = false;
                    break;
                default:
                    var x = Integer.parseInt(matcher.group(1));
                    var y = Integer.parseInt(matcher.group(2));
                    instructions.add(new Mul(x, y));
                    if (enabled)
                        enabledInstructions.add(new Mul(x, y));
                    break;
            }
        }
    }

    public String part1() {
        long ans = 0;
        for (var mul : instructions) {
            ans += mul.x() * mul.y();
        }
        return String.valueOf(ans);
    }

    public String part2() {
        long ans = 0;
        for (var mul : enabledInstructions) {
            ans += mul.x() * mul.y();
        }
        return String.valueOf(ans);
    }
}
