package com.macher259.aoc2024;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day8 {
    private final char[][] map;
    private final Map<Character, Set<Position>> antennas = new HashMap<>();

    public Day8(String data) {
        map = data.lines().map(String::toCharArray).toArray(char[][]::new);

        int n = map.length;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                char freq = map[row][col];
                if (freq != '.') {
                    antennas.putIfAbsent(freq, new HashSet<>());
                    antennas.get(freq).add(new Position(row, col));
                }
            }
        }
    }

    private boolean isPositionInBounds(Position pos) {
        return pos.row >= 0 && pos.row < map.length && pos.col >= 0 && pos.col < map.length;
    }

    public String part1() {
        Set<Position> antinodes = new HashSet<>();
        for (var ants : antennas.values()) {
            for (var ant1 : ants) {
                for (var ant2 : ants) {
                    if (ant1 == ant2)
                        continue;
                    int rowDist = ant1.row() - ant2.row();
                    int colDist = ant1.col() - ant2.col();

                    var pos1 = new Position(ant1.row() + rowDist, ant1.col() + colDist);
                    var pos2 = new Position(ant2.row() - rowDist, ant2.col() - colDist);

                    if (isPositionInBounds(pos1)) {
                        antinodes.add(pos1);
                    }
                    if (isPositionInBounds(pos2)) {
                        antinodes.add(pos2);
                    }
                }
            }
        }
        return String.valueOf(antinodes.size());
    }

    public String part2() {
        Set<Position> antinodes = new HashSet<>();
        for (var ants : antennas.values()) {
            for (var ant1 : ants) {
                for (var ant2 : ants) {
                    if (ant1 == ant2)
                        continue;
                    int rowDist = ant1.row() - ant2.row();
                    int colDist = ant1.col() - ant2.col();

                    var pos1 = new Position(ant1.row() + rowDist, ant1.col() + colDist);
                    var pos2 = new Position(ant1.row() - rowDist, ant1.col() - colDist);

                    while (isPositionInBounds(pos1)) {
                        antinodes.add(pos1);
                        pos1 = new Position(pos1.row() + rowDist, pos1.col() + colDist);
                    }
                    while (isPositionInBounds(pos2)) {
                        antinodes.add(pos2);
                        pos2 = new Position(pos2.row() - rowDist, pos2.col() - colDist);
                    }
                }
            }
        }
        return String.valueOf(antinodes.size());
    }

    record Position(int row, int col) {
    }
}
