package com.staxrt.tutorial.util;

import java.util.Random;

public class RandomCode {
    static Random random = new Random();

    public static String get() {
        Random random = new Random();
        StringBuilder codigoBuilder = new StringBuilder();

        // Generar 4 caracteres aleatorios
        for (int i = 0; i < 4; i++) {
            // Generar un número aleatorio entre 0 y 35
            int caracterAleatorio = random.nextInt(36);

            // Si el número es menor que 10, añadir un dígito al código
            if (caracterAleatorio < 10) {
                codigoBuilder.append(caracterAleatorio);
            } else {
                // Si el número es mayor o igual a 10, añadir una letra al código
                char letraAleatoria = (char) ('A' + (caracterAleatorio - 10));
                codigoBuilder.append(letraAleatoria);
            }
        }

        return codigoBuilder.toString();
    }

}
