import java.util.Vector;
import java.util.Scanner;

public class Processadora extends Thread { // Classes processadoras são as ordenadoras, que recebem um vetor e ordenam
                                           // uma parte dele.
    private Vector<Byte> vetor;
    private int inicio;
    private int fim;
    private long duracao;

    /*
        Explicação da lógica de merge:
        Exemplo: vetor = [5, 2, 9, 1, 5, 6], inicio = 0, fim = 5
        1. O método mergeSort é chamado com os parâmetros (vetor, 0, 5).
        2. O meio é calculado como (0 + 5) / 2
        3. O método mergeSort é chamado recursivamente para a primeira metade (vetor, 0, 2) em mergeSort(vetor, inicio, meio).
        4. O método mergeSort é chamado recursivamente para a segunda metade (vetor, 3, 5) em mergeSort(vetor, meio + 1, fim).
        5. O método merge é chamado para combinar as duas metades ordenadas.
        6. Dentro do método merge, dois vetores temporários são criados: esq = [5, 2, 9] e dir = [1, 5, 6].
        7. Os elementos dos vetores esq e dir são comparados e o menor elemento é colocado de volta no vetor original.
        8. O processo continua até que todos os elementos de esq e dir tenham sido colocados de volta no vetor original, resultando em um vetor ordenado: [1, 2, 5, 5, 6, 9].
    */
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
                vetor.set(k, dir[j]); // enquanto não alcançar o final de nenhum dos vetores, compara os elementos e
                                      // coloca o menor no vetor original.
                j++;
            }
            k++; // incrementa o índice do vetor original
        }

        while (i < esq.length) { // enquanto não alcançar o final do vetor esquerdo, coloca os elementos restantes no vetor original.
            vetor.set(k, esq[i]); 
            i++;
            k++;
        }

        while (j < dir.length) { // enquanto não alcançar o final do vetor direito, coloca os elementos restantes no vetor original.
            vetor.set(k, dir[j]);
            j++;
            k++;
        }

    }

    public  Vector<Byte> getParteVetor() { // retorna a parte do vetor que foi ordenada por esta thread
        Vector<Byte> parteVetor = new Vector<>();
        for (int i = inicio; i < fim; i++) {
            parteVetor.add(vetor.get(i));
        }
        return parteVetor;
    }

    @Override
    public void run() {
        long inicioTempo = System.currentTimeMillis();
        mergeSort(vetor, inicio, fim - 1);
        long fimTempo = System.currentTimeMillis();
        long duracao = fimTempo - inicioTempo;
        this.duracao = duracao;
        System.out.println(getName() + " ordenou [" + inicio + ", " + (fim - 1) + "]");
        
    }
}