package cartao;

import cliente.Cliente;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CartaoVirtual extends Cartao {
    private double limite;

    public CartaoVirtual(Cliente cliente, double limite) {
        super(gerarNumeroAleatorio(), gerarValidade(), gerarCvvAleatorio(), cliente);
        this.limite = limite;
    }

    public double getLimite() {
        return limite;
    }

    private static String gerarNumeroAleatorio() {
        Random rand = new Random();
        return "4000 " + (1000 + rand.nextInt(9000)) + " " + (1000 + rand.nextInt(9000)) + " " + (1000 + rand.nextInt(9000));
    }

    private static String gerarValidade() {
        return LocalDate.now().plusYears(2).format(DateTimeFormatter.ofPattern("MM/yy"));
    }

    private static String gerarCvvAleatorio() {
        return String.format("%03d", new Random().nextInt(1000));
    }

    @Override
    public String toString() {
        return "Cartao Virtual [Numero: " + getNumero() + " | Validade: " + getValidade() + 
               " | CVV: " + getCvv() + " | Limite: R$ " + String.format("%.2f", limite) + "]";
    }
}

