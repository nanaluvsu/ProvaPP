import java.util.Scanner;
import java.util.Vector;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProgramaSemParalelismo {
    private static final Logger LOGGER = LogUtil.logger(ProgramaSemParalelismo.class);
    private long duracao;

    public long getDuracao() { return duracao; }

    private void mergeSort(Vector<Byte> vetor, int inicio, int fim) {
        if (inicio >= fim) return;
        int meio = inicio + (fim - inicio) / 2;
        mergeSort(vetor, inicio, meio);
        mergeSort(vetor, meio + 1, fim);
        merge(vetor, inicio, meio, fim);
    }

    private void merge(Vector<Byte> vetor, int inicio, int meio, int fim) {
        if (vetor.isEmpty()) return;
        byte[] esq = new byte[meio - inicio + 1];
        byte[] dir = new byte[fim - meio];
        for (int i = 0; i < esq.length; i++) esq[i] = vetor.get(inicio + i);
        for (int i = 0; i < dir.length; i++) dir[i] = vetor.get(meio + 1 + i);
        int i = 0, j = 0, k = inicio;
        while (i < esq.length && j < dir.length) {
            if (esq[i] <= dir[j]) vetor.set(k++, esq[i++]);
            else vetor.set(k++, dir[j++]);
        }
        while (i < esq.length) vetor.set(k++, esq[i++]);
        while (j < dir.length) vetor.set(k++, dir[j++]);
    }

    public void mergeExecute() {
        Scanner teclado = LogUtil.scanner();
        try {
            Vector<Byte> vetor = new Vector<>();
            System.out.println("Digite o tamanho do vetor:");
            int size = lerInteiro(teclado, "tamanho do vetor");
            if (size <= 0) {
                LOGGER.warning("Tamanho de vetor inválido: " + size);
                System.out.println("O tamanho do vetor deve ser maior que zero.");
                return;
            }
            System.out.println("Deseja preencher o vetor manualmente ou com valores aleatorios?");
            System.out.println("[1] Manualmente [2] Aleatorios");
            System.out.println("Em caso de opcao invalida, o vetor sera preenchido com valores aleatorios.");
            int opcao = lerInteiro(teclado, "opção de preenchimento");
            if (opcao == 1) {
                for (int i = 0; i < size; i++) {
                    System.out.printf("Digite o valor do elemento %d (-128 a 127): ", i);
                    int entrada = lerInteiro(teclado, "elemento do vetor");
                    if (entrada < Byte.MIN_VALUE || entrada > Byte.MAX_VALUE) {
                        LOGGER.warning("Valor fora do intervalo byte no índice " + i + ": " + entrada);
                        System.out.println("Valor inválido. Digite um número entre -128 e 127.");
                        i--;
                        continue;
                    }
                    vetor.add((byte) entrada);
                }
            } else {
                if (opcao != 2) LOGGER.warning("Opção inválida (" + opcao + "); usando valores aleatórios.");
                for (int i = 0; i < size; i++) vetor.add((byte) (Math.random() * 100));
            }

            LOGGER.info("Iniciando Merge Sort sem paralelismo com " + vetor.size() + " elementos.");
            long inicioTempo = System.currentTimeMillis();
            mergeSort(vetor, 0, vetor.size() - 1);
            duracao = System.currentTimeMillis() - inicioTempo;
            LOGGER.info("Merge Sort sem paralelismo concluído em " + duracao + " ms.");

            System.out.println("Deseja imprimir o vetor ordenado? [1] Sim [2] Nao");
            opcao = lerInteiro(teclado, "opção de impressão do vetor");
            if (opcao == 1) {
                System.out.println("Vetor ordenado:");
                for (int i = 0; i < vetor.size(); i++) {
                    System.out.print(vetor.get(i) + " ");
                    if ((i + 1) % 25 == 0) System.out.println();
                }
                System.out.println();
            } else if (opcao != 2) {
                LOGGER.warning("Opção inválida para impressão do vetor: " + opcao);
            }
            System.out.println("Deseja imprimir o tempo de execucao? [1] Sim [2] Nao");
            opcao = lerInteiro(teclado, "opção de impressão do tempo");
            if (opcao == 1) System.out.println("Tempo de execucao: " + duracao + " ms");
            else if (opcao != 2) LOGGER.warning("Opção inválida para impressão do tempo: " + opcao);
        } catch (InputMismatchException e) {
            LOGGER.log(Level.WARNING, "Entrada inválida no modo sem paralelismo.", e);
            System.out.println("Entrada inválida. Execute novamente e informe números inteiros válidos.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            LOGGER.log(Level.SEVERE, "Erro no processamento sem paralelismo.", e);
            System.out.println("Não foi possível concluir a operação: " + e.getMessage());
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Erro inesperado no modo sem paralelismo.", e);
            System.out.println("Ocorreu um erro inesperado durante a ordenação.");
        }
    }

    private int lerInteiro(Scanner teclado, String campo) {
        if (!teclado.hasNextInt()) {
            String recebido = teclado.hasNext() ? teclado.next() : "fim da entrada";
            LOGGER.warning("Entrada não inteira para " + campo + ": " + recebido);
            throw new InputMismatchException("Era esperado um número inteiro para " + campo + ".");
        }
        return teclado.nextInt();
    }
}
