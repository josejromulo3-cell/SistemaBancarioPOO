package util;

import java.util.Random;

public class GeradorNumeroConta {
    private static final Random random = new Random();

    public static String gerarNumero() {
        int numero = 10000 + random.nextInt(90000);
        int digito = random.nextInt(10);
        return numero + "-" + digito;
    }
}
