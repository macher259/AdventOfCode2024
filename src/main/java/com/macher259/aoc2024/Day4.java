package com.macher259.aoc2024;

import java.util.Arrays;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Day4 {
    char[][] matrix;

    public Day4(String data) {
        matrix = data.lines().map(String::toCharArray).toArray(char[][]::new);
    }

    private boolean isInBounds(int x, int y) {
        return min(x, y) >= 0 && max(x, y) < matrix.length;
    }

    private int walk(int startX, int startY, int dx, int dy, char[] pattern) {
        int x = startX;
        int y = startY;
        int state = 0;
        while (isInBounds(x, y)) {
            if (matrix[x][y] == pattern[state]) {
                state++;
                if (state == pattern.length) {
                    return 1;
                }
            } else {
                return 0;
            }
            x += dx;
            y += dy;
        }
        return 0;
    }

    public String part1() {
        int ans = 0;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                for (int dx : new int[]{-1, 0, 1}) {
                    for (int dy : new int[]{-1, 0, 1}) {
                        if (dx == 0 && dy == 0) {
                            continue;
                        }
                        ans += walk(i, j, dx, dy, "XMAS".toCharArray());
                    }
                }
            }
        }
        return String.valueOf(ans);
    }

    public String part2() {
        int ans = 0;
        var pattern = "MS".toCharArray();
        Arrays.sort(pattern);
        for (int i = 1; i < matrix.length - 1; i++) {
            for (int j = 1; j < matrix.length - 1; j++) {
                if (matrix[i][j] == 'A') {
                    var arr = new char[]{matrix[i + 1][j + 1], matrix[i - 1][j - 1]};
                    var arr2 = new char[]{matrix[i - 1][j + 1], matrix[i + 1][j - 1]};
                    Arrays.sort(arr);
                    Arrays.sort(arr2);
                    if (Arrays.equals(arr, pattern) && Arrays.equals(arr2, pattern)) {
                        ans++;
                    }

                }
            }
        }
        return String.valueOf(ans);
    }
}
