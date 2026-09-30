import java.util.Vector;
import java.util.Scanner;

public class Processadora extends Thread {
    private Vector<Byte> vetor;
    private int inicio;
    private int fim;
    private byte maior;

    public Processadora(Vector<Byte> vetor) {
        this.vetor = vetor;

        
    }

    public byte getMaior() {
        return maior;
    }

    @Override
    public void run() {
        long inicioTempo = System.currentTimeMillis();

        long fimTempo = System.currentTimeMillis();
        long duracao = fimTempo - inicioTempo;
        System.out.printf("Thread %s terminou em %d ms%n", Thread.currentThread().getName(), duracao);
    }
}