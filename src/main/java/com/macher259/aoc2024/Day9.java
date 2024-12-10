package com.macher259.aoc2024;


import java.util.*;

import static java.lang.Character.getNumericValue;

public class Day9 {
    private final char[] diskMap;
    private final List<Integer> disk = new ArrayList<>();

    public Day9(String data) {
        diskMap = data.toCharArray();

        int curId = 0;
        for (int i = 0; i < diskMap.length; i++) {
            int size = getNumericValue(diskMap[i]);
            if (i % 2 == 0) {
                disk.addAll(Collections.nCopies(size, curId));
                curId++;
            } else {
                disk.addAll(Collections.nCopies(size, -1));
            }
        }
    }

    public String part1() {
        int tail = disk.size() - 1;
        int head = 0;

        while (head < tail) {
            int headId = disk.get(head);
            int tailId = disk.get(tail);
            if (headId != -1) {
                head++;
            } else if (tailId == -1) {
                tail--;
            } else {
                disk.set(head, tailId);
                disk.set(tail, -1);
                tail--;
                head++;
            }
        }

        long checkSum = 0;
        long id = 0;
        for (int block : disk) {
            if (block != -1) {
                checkSum += block * id;
            }
            id++;
        }
        return String.valueOf(checkSum);
    }

    private int findNextFile(int head, Set<Integer> usedIds, int size, List<Integer> formattedDisk) {
        for (int i = diskMap.length; i > head; i--) {
            if (usedIds.contains(i) || i % 2 == 1) {
                continue;
            }
            int candidateSize = getNumericValue(diskMap[i]);
            if (candidateSize > size) {
                continue;
            }

            formattedDisk.addAll(Collections.nCopies(candidateSize, (i + 1) / 2));
            usedIds.add(i);
            return size - candidateSize;
        }

        return size;
    }

    public String part2() {
        int head = 0;
        List<Integer> formattedDisk = new ArrayList<>();
        Set<Integer> usedIds = new HashSet<>();
        int curId = 0;
        while (head < diskMap.length) {
            int size = getNumericValue(diskMap[head]);
            if (usedIds.contains(head)) {
                formattedDisk.addAll(Collections.nCopies(size, 0));
                head++;
                continue;
            }

            if (head % 2 == 0) {
                usedIds.add(head);
                formattedDisk.addAll(Collections.nCopies(size, head / 2));
                head++;
            } else {
                int sizeLeft = findNextFile(head, usedIds, size, formattedDisk);
                if (sizeLeft == 0) {
                    head++;
                } else if (sizeLeft == size) {
                    formattedDisk.addAll(Collections.nCopies(sizeLeft, 0));
                    head++;
                } else {
                    diskMap[head] = (char) (sizeLeft + '0');
                }
            }
        }
        long checkSum = 0;
        long id = 0;
        for (int block : formattedDisk) {
            if (block != -1) {
                checkSum += block * id;
            }
            id++;
        }
        return String.valueOf(checkSum);
    }

}
