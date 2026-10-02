import java.util.Vector;
import java.util.Scanner;

public class Processadora extends Thread { // Classes processadoras são as ordenadoras, que recebem um vetor e ordenam
                                           // uma parte dele.
    private Vector<Byte> vetor;
    private int inicio;
    private int fim;
    private long duracao;

    public Processadora(Vector<Byte> vetor, int inicio, int fim) {
        this.vetor = vetor;
        this.inicio = inicio;
        this.fim = fim;
    }

    public long getDuracao() {
        return duracao;
    }

    private void mergeSort(Vector<Byte> vetor, int inicio, int fim) {
        if (inicio >= fim) { 
            return;
        }
        int meio = (inicio + fim) / 2; // divide o vetor em duas partes
        mergeSort(vetor, inicio, meio);
        mergeSort(vetor, meio + 1, fim);
        merge(vetor, inicio, meio, fim);

    }

    private void merge(Vector<Byte> vetor, int inicio, int meio, int fim) {
        if (vetor.size() <= 0) {
            return; // Se o vetor estiver vazio, não há nada a ser feito.
        }
        byte[] esq = new byte[meio - inicio + 1];
        byte[] dir = new byte[fim - meio];

        for (int i = 0; i < esq.length; i++) {
            esq[i] = vetor.get(inicio + i);
        }
        for (int i = 0; i < dir.length; i++) {
            dir[i] = vetor.get(meio + 1 + i);
        }

        int i = 0, j = 0, k = inicio;
        while (i < esq.length && j < dir.length) {
            if (esq[i] <= dir[j]) {
                vetor.set(k, esq[i]); // enquanto não alcançar o final de nenhum dos vetores, compara os elementos e
                                      // coloca o menor no vetor original.
                i++;
            } else {
                vetor.set(k, dir[j]);
                j++;
            }
            k++;
        }

        while (i < esq.length) {
            vetor.set(k, esq[i]);
            i++;
            k++;
        }

        while (j < dir.length) {
            vetor.set(k, dir[j]);
            j++;
            k++;
        }

    }

    @Override
    public void run() {
        long inicioTempo = System.currentTimeMillis();
        mergeSort(vetor, inicio, fim - 1);
        System.out.println(getName() + " ordenou [" + inicio + ", " + (fim - 1) + "]");
        long fimTempo = System.currentTimeMillis();
        long duracao = fimTempo - inicioTempo;
        System.out.println(getName() + " duracao: " + duracao + " ms");
    }
}