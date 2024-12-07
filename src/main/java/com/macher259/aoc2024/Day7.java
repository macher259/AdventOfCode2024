package com.macher259.aoc2024;

import java.util.List;

record Equation(long res, long[] factors) {
    public Equation(String strEquation) {
        var parts = strEquation.split(": ");
        var res = Long.parseLong(parts[0]);
        var strFactors = parts[1].split(" ");
        var factors = new long[strFactors.length];
        for (int i = 0; i < strFactors.length; i++) {
            factors[i] = Long.parseLong(strFactors[i]);
        }
        this(res, factors);
    }

    public boolean isValid() {
        return isValid(factors[0], 1, false);
    }

    public boolean isValidWithConcat() {
        return isValid(factors[0], 1, true);
    }

    private boolean isValid(long curRes, int usedNumbers, boolean useConcat) {
        if (curRes == res && usedNumbers == factors.length) {
            return true;
        }
        if (usedNumbers == factors.length || curRes > res) {
            return false;
        }
        return isValid(curRes + factors[usedNumbers], usedNumbers + 1, useConcat)
                || isValid(curRes * factors[usedNumbers], usedNumbers + 1, useConcat)
                || (useConcat && isValid(concat(curRes, factors[usedNumbers]), usedNumbers + 1, true));
    }

    private long concat(long a, long b) {
        long c = b;
        while (c > 0) {
            a *= 10;
            c /= 10;
        }
        return a + b;
    }
}

public class Day7 {
    private final List<Equation> equations;

    public Day7(String data) {
        equations = data.lines()
                .map(Equation::new)
                .toList();
    }

    public String part1() {
        var sumOfValidEquations = equations.stream()
                .filter(Equation::isValid)
                .mapToLong(Equation::res)
                .sum();
        return String.valueOf(sumOfValidEquations);
    }

    public String part2() {
        var sumOfValidEquations = equations.stream()
                .filter(Equation::isValidWithConcat)
                .mapToLong(Equation::res)
                .sum();
        return String.valueOf(sumOfValidEquations);
    }
}
