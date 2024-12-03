package com.macher259.aoc2024;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

public class Main {
    private static final int LAST_DAY = 3;

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, IllegalAccessException, InstantiationException {
        for (int i = 1; i <= LAST_DAY; i++) {
            exec(i);
        }
    }

    private static void exec(int num) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("day" + num + ".in")) {
            var data = new String(input.readAllBytes());
            var day = Class.forName("com.macher259.aoc2024.Day" + num);
            var obj = day.getDeclaredConstructor(String.class).newInstance(data);

            for (int i = 1; i <= 2; i++) {
                var method = day.getDeclaredMethod("part" + i);
                var result = method.invoke(obj);
                System.out.println("Day " + num + " part " + i + ": " + result);
            }
        }
    }
}
