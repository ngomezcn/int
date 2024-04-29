package com.staxrt.tutorial.util;

import java.util.Random;

public class RandomCode {
    static Random random = new Random();

    public static String get()
    {
        return String.valueOf(10000 + random.nextInt(90000));
    }
}
