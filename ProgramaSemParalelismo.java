import java.util.Scanner;
import java.util.Vector;
import java.io.FileWriter;
import java.io.IOException;

public class ProgramaSemParalelismo {

    private void mergeSort(Vector<Byte> vetor, int inicio, int fim) {
        if (inicio >= fim) {
            return;
        }
        int meio = (inicio + fim) / 2;
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

    public static void main(String[] args) {
        
        Scanner teclado = new Scanner(System.in);
        Vector<Byte> vetor = new Vector<>();
        System.out.println("Digite o tamanho do vetor: ");
        int size = teclado.nextInt();
        System.out.println("Deseja preencher o vetor manualmente ou com valores aleatorios?");
        System.out.println("[1] Manualmente [2] Aleatorios");
        System.out.println("Em caso de opcao invalida, o vetor sera preenchido com valores aleatorios.");
        int opcao = teclado.nextInt();
        if (opcao == 1) {
            for (int i = 0; i < size; i++) {
                System.out.printf("Digite o valor do elemento %d: ", i);
                byte valor = teclado.nextByte();
                vetor.add(valor);
            }
        } else {
            for (int i = 0; i < size; i++) {
                byte valor = (byte) (Math.random() * 100);
                vetor.add(valor);
            }
        }

        ProgramaSemParalelismo programa = new ProgramaSemParalelismo();
        long inicioTempo = System.currentTimeMillis();
        programa.mergeSort(vetor, 0, vetor.size() - 1);
        long fimTempo = System.currentTimeMillis();

        long duracao = fimTempo - inicioTempo;

        System.out.println("Deseja imprimir o vetor ordenado? [1] Sim [2] Nao");
        opcao = teclado.nextInt();
        if (opcao == 1) { //Exibe o vetor quebrado em linhas de 25 elementos

            System.out.println("Vetor ordenado: ");
            for (int i = 0; i < vetor.size(); i++) {
                System.out.print(vetor.get(i) + " ");
                if ((i + 1) % 25 == 0) {
                    System.out.println();
                }
            }
        }
        System.out.println("Deseja imprimir o tempo de execucao? [1] Sim [2] Nao");
        opcao = teclado.nextInt();
        if (opcao == 1) {
            System.out.println("Tempo de execucao: " + duracao + " ms");
        }

        teclado.close();
    }
}
