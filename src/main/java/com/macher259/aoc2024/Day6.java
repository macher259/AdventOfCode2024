package com.macher259.aoc2024;

import java.util.*;
import java.util.stream.Stream;

enum Direction {
    UP, RIGHT, DOWN, LEFT
}

record Pair(int row, int col) {
    Pair on(Direction dir) {
        return switch (dir) {
            case UP -> new Pair(row - 1, col);
            case RIGHT -> new Pair(row, col + 1);
            case DOWN -> new Pair(row + 1, col);
            case LEFT -> new Pair(row, col - 1);
        };
    }
}

public class Day6 {
    private final char[][] matrix;
    private final List<TreeSet<Integer>> rowObstacles;
    private final List<TreeSet<Integer>> colObstacles;

    private int startRow;
    private int startCol;

    public Day6(String data) {
        matrix = data.lines().map(String::toCharArray).toArray(char[][]::new);
        rowObstacles = Stream.generate(TreeSet<Integer>::new).limit(matrix.length).toList();
        colObstacles = Stream.generate(TreeSet<Integer>::new).limit(matrix.length).toList();

        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                if (matrix[row][col] == '^') {
                    startCol = col;
                    startRow = row;
                }
                if (matrix[row][col] == '#') {
                    rowObstacles.get(row).add(col);
                    colObstacles.get(col).add(row);
                }
            }
        }
    }

    private boolean isOnEdge(int pos) {
        return pos == 0 || pos == matrix.length - 1;
    }

    private void printMap(int row, int col) {
        System.out.println();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i == row && j == col) {
                    System.out.print("X");
                } else {
                    System.out.print(matrix[i][j]);
                }
            }
            System.out.println();
        }
    }

    private boolean walk(Pair startPos, Set<Pair> usedPositions) {
        var position = startPos;
        usedPositions.add(startPos);
        var direction = Direction.UP;
        var loopMap = new EnumMap<Direction, Set<Pair>>(Direction.class);

        while (!isOnEdge(position.col()) && !isOnEdge(position.row())) {
            loopMap.putIfAbsent(direction, new HashSet<>());
            if (loopMap.get(direction).contains(position)) {
                return true;
            }
            switch (direction) {
                case UP -> {
                    int nextObstacle = Optional.ofNullable(colObstacles.get(position.col()).lower(position.row())).orElse(-1);
                    for (; position.row() > nextObstacle; position = position.on(Direction.UP)) {
                        usedPositions.add(position);
                        loopMap.get(Direction.UP).add(position);
                    }
                    position = position.on(Direction.DOWN);
                    direction = Direction.RIGHT;
                }
                case RIGHT -> {
                    int nextObstacle = Optional.ofNullable(rowObstacles.get(position.row()).higher(position.col())).orElse(matrix.length);
                    for (; position.col() < nextObstacle; position = position.on(Direction.RIGHT)) {
                        usedPositions.add(position);
                        loopMap.get(Direction.RIGHT).add(position);
                    }
                    position = position.on(Direction.LEFT);
                    direction = Direction.DOWN;
                }
                case DOWN -> {
                    int nextObstacle = Optional.ofNullable(colObstacles.get(position.col()).higher(position.row())).orElse(matrix.length);
                    for (; position.row() < nextObstacle; position = position.on(Direction.DOWN)) {
                        usedPositions.add(position);
                        loopMap.get(Direction.DOWN).add(position);
                    }
                    position = position.on(Direction.UP);
                    direction = Direction.LEFT;
                }
                case LEFT -> {
                    int nextObstacle = Optional.ofNullable(rowObstacles.get(position.row()).lower(position.col())).orElse(-1);
                    for (; position.col() > nextObstacle; position = position.on(Direction.LEFT)) {
                        usedPositions.add(position);
                        loopMap.get(Direction.LEFT).add(position);
                    }
                    position = position.on(Direction.RIGHT);
                    direction = Direction.UP;
                }
            }
            //printMap(position.row(), position.col());
        }

        return false;
    }

    public String part1() {
        var position = new Pair(startRow, startCol);
        var usedPositions = new HashSet<Pair>();
        walk(position, usedPositions);

        return String.valueOf(usedPositions.size());
    }

    public String part2() {
        int ans = 0;
        var startPos = new Pair(startRow, startCol);
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix.length; col++) {
                boolean wasRowObstacle = rowObstacles.get(row).contains(col);
                boolean wasColObstacle = colObstacles.get(col).contains(row);
                rowObstacles.get(row).add(col);
                colObstacles.get(col).add(row);
                if (walk(startPos, new HashSet<>())) {
                    ans++;
                }
                if (!wasRowObstacle) {
                    rowObstacles.get(row).remove(col);
                }
                if (!wasColObstacle) {
                    colObstacles.get(col).remove(row);
                }
            }
        }

        return String.valueOf(ans);
    }
}
