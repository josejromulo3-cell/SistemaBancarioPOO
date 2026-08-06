package util;

import java.util.Random;

/**
 * Utilitário para geração aleatória de números de contas.
 * 
 * Fonte: bancoterminal (https://github.com/andrezzahfreire/bancoterminal)
 */
public class GeradorNumeroConta {
    private static final Random random = new Random();

    public static String gerar() {
        int numero = 10000 + random.nextInt(90000);
        return String.valueOf(numero);
    }
}
