package util;

import java.util.Random;

public class GeradorCartao {
    private static final Random random = new Random();

    public static String gerarNumero() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String gerarCvv() {
        return String.format("%03d", random.nextInt(1000));
    }

    public static String gerarValidade() {
        int mes = 1 + random.nextInt(12);
        int ano = 28 + random.nextInt(5); 
        return String.format("%02d/%d", mes, ano);
    }
}
