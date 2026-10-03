
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

public class ProgramaSemParalelismo {

    private Scanner teclado;
    private long duracao;

    public ProgramaSemParalelismo(Scanner teclado) {
        this.teclado = teclado;
    }

    public long getDuracao() {
        return duracao;
    }

    private void mergeSort(Vector<Byte> vetor, int inicio, int fim) {
        if (inicio >= fim) {
            return;
        }

        int meio = inicio + (fim - inicio) / 2;

        mergeSort(vetor, inicio, meio);
        mergeSort(vetor, meio + 1, fim);

        merge(vetor, inicio, meio, fim);
    }

    private void merge(Vector<Byte> vetor, int inicio, int meio, int fim) {
        if (inicio >= fim) {
            return;
        }

        byte[] esq = new byte[meio - inicio + 1];
        byte[] dir = new byte[fim - meio];

        for (int i = 0; i < esq.length; i++) {
            esq[i] = vetor.get(inicio + i);
        }

        for (int i = 0; i < dir.length; i++) {
            dir[i] = vetor.get(meio + 1 + i);
        }

        int i = 0;
        int j = 0;
        int k = inicio;

        while (i < esq.length && j < dir.length) {
            if (esq[i] <= dir[j]) {
                vetor.set(k++, esq[i++]);
            } else {
                vetor.set(k++, dir[j++]);
            }
        }

        while (i < esq.length) {
            vetor.set(k++, esq[i++]);
        }

        while (j < dir.length) {
            vetor.set(k++, dir[j++]);
        }
    }

    public void mergeExecute() {
        try {
            Vector<Byte> vetor = new Vector<>();

            System.out.println("Digite o tamanho do vetor:");
            int size = lerInteiro("tamanho do vetor");

            if (size <= 0) {
                System.out.println("O tamanho do vetor deve ser maior que zero.");
                return;
            }

            System.out.println(
                "Deseja preencher o vetor manualmente ou com valores aleatórios?"
            );
            System.out.println("[1] Manualmente [2] Aleatórios");
            System.out.println(
                "Em caso de opção inválida, o vetor será preenchido aleatoriamente."
            );

            int opcao = lerInteiro("opção de preenchimento");

            if (opcao == 1) {
                for (int i = 0; i < size; i++) {
                    System.out.printf(
                        "Digite o valor do elemento %d (-128 a 127): ",
                        i
                    );

                    int entrada = lerInteiro("elemento do vetor");

                    if (entrada < Byte.MIN_VALUE || entrada > Byte.MAX_VALUE) {
                        System.out.println(
                            "Valor inválido. Digite um número entre -128 e 127."
                        );
                        i--;
                        continue;
                    }

                    vetor.add((byte) entrada);
                }

            } else {
                if (opcao != 2) {
                    System.out.println(
                        "Opção inválida. O vetor será preenchido aleatoriamente."
                    );
                }

                for (int i = 0; i < size; i++) {
                    byte valor = (byte) (Math.random() * 256 - 128);
                    vetor.add(valor);
                }
            }

            System.out.println("Iniciando Merge Sort sem paralelismo...");

            long inicioTempo = System.currentTimeMillis();

            mergeSort(vetor, 0, vetor.size() - 1);

            duracao = System.currentTimeMillis() - inicioTempo;

            System.out.println("Ordenação concluída.");

            System.out.println("Deseja imprimir o vetor ordenado? [1] Sim [2] Não");
            opcao = lerInteiro("opção de impressão do vetor");

            if (opcao == 1) {
                System.out.println("Vetor ordenado:");

                for (int i = 0; i < vetor.size(); i++) {
                    System.out.print(vetor.get(i) + " ");

                    if ((i + 1) % 25 == 0) {
                        System.out.println();
                    }
                }

                System.out.println();

            } else if (opcao != 2) {
                System.out.println("Opção de impressão inválida.");
            }

            System.out.println("Deseja imprimir o tempo de execução? [1] Sim [2] Não");
            opcao = lerInteiro("opção de impressão do tempo");

            if (opcao == 1) {
                System.out.println("Tempo de execução: " + duracao + " ms");

            } else if (opcao != 2) {
                System.out.println("Opção de impressão do tempo inválida.");
            }

        } catch (InputMismatchException e) {
            System.out.println(
                "Entrada inválida. Informe números inteiros nos campos solicitados."
            );

        } catch (IllegalArgumentException e) {
            System.out.println(
                "Argumento inválido: " + e.getMessage()
            );

        } catch (RuntimeException e) {
            System.out.println(
                "Ocorreu um erro durante a ordenação: " + e.getMessage()
            );
        }
    }

    private int lerInteiro(String campo) {
        if (!teclado.hasNextInt()) {
            if (teclado.hasNext()) {
                teclado.next();
            }

            throw new InputMismatchException(
                "Era esperado um número inteiro para " + campo + "."
            );
        }

        return teclado.nextInt();
    }
}
