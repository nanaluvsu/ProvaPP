import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;

public class ProgramaSemParalelismo {
    private Scanner teclado;

    private long duracao;

    public long getDuracao() {
        return duracao;
    }

    public ProgramaSemParalelismo(Scanner teclado) {
        this.teclado = teclado;
    }

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

    public void mergeExecute() {
        int printOpcao, tempoOpcao, size;
        Vector<Byte> vetor = new Vector<>();
        while (true) {
            System.out.println("Digite o tamanho do vetor: ");
            try {
                size = teclado.nextInt();
                if (size <= 0) {
                    System.out.println("Tamanho do vetor deve ser um numero positivo.");
                    teclado.nextLine();
                    continue;
                }
                break;
            } catch (InputMismatchException ex) {
                System.out.println("Entrada invalida. Digite um numero inteiro.");
                teclado.nextLine();
            }
        }

        System.out.println("Deseja preencher o vetor manualmente ou com valores aleatorios?");
        System.out.println("[1] Manualmente [2] Aleatorios");
        System.out.println("Em caso de opcao invalida, o vetor sera preenchido com valores aleatorios.");

        int opcaoPreenchimento = 0;

        while (true) {
            try {
                opcaoPreenchimento = teclado.nextInt();

                if (opcaoPreenchimento == 1) {
                    for (int i = 0; i < size; i++) {
                        while (true) {
                            try {
                                System.out.printf("Digite o valor do elemento %d: ", i);
                                byte valor = teclado.nextByte();
                                vetor.add(valor);
                                break;
                            } catch (InputMismatchException ex) {
                                System.out.println("Valor invalido. Digite um numero entre -128 e 127.");
                                teclado.nextLine();
                            }
                        }
                    }
                    break;
                } else if (opcaoPreenchimento == 2) {
                    for (int i = 0; i < size; i++) {
                        byte valor = (byte) (Math.random() * 256 - 128);
                        vetor.add(valor);
                    }
                    break;
                } else {
                    System.out.println("Opcao invalida. O vetor sera preenchido com valores aleatorios.");
                    for (int i = 0; i < size; i++) {
                        byte valor = (byte) (Math.random() * 256 - 128);
                        vetor.add(valor);
                    }
                    break;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Entrada invalida. Digite 1 ou 2.");
                teclado.nextLine();
            }
        }

        // long inicioTempo = System.currentTimeMillis(); // Tempo em ms está em 0,
        // testando com nano
        long inicioTempo = System.currentTimeMillis(); // Tempo em ns
        mergeSort(vetor, 0, vetor.size() - 1);
        long fimTempo = System.currentTimeMillis();

        long duracao = fimTempo - inicioTempo;
        this.duracao = duracao;

        while (true) {
    try {
        System.out.println("Deseja imprimir:");
        System.out.println("[1] Todo o vetor");
        System.out.println("[2] Parte especifica do vetor");
        printOpcao = teclado.nextInt();

        if (printOpcao == 1 || printOpcao == 2) {
            break;
        }

        System.out.println("Entrada invalida. Digite 1 ou 2.");
    } catch (InputMismatchException ex) {
        System.out.println("Entrada invalida. Digite 1 ou 2.");
        teclado.nextLine();
    }
}

if (printOpcao == 1) {
    for (int i = 0; i < vetor.size(); i++) {
        System.out.print(vetor.get(i) + " ");
    }
} else {
    int inicio, fim;

    while (true) {
        try {
            System.out.println("Digite o indice inicial: ");
            inicio = teclado.nextInt();

            System.out.println("Digite o indice final: ");
            fim = teclado.nextInt();

            if (inicio >= 0 && fim >= inicio && fim < vetor.size()) {
                break;
            }

            System.out.println("Intervalo invalido.");
        } catch (InputMismatchException ex) {
            System.out.println("Digite valores inteiros validos.");
            teclado.nextLine();
        }
    }

    for (int i = inicio; i <= fim; i++) {
        System.out.print(vetor.get(i) + " ");
    }
}
        while (true) {
            try {
                System.out.println(
                        "\nDeseja imprimir o tempo de execucao do programa? [1] Sim [2] Nao");
                tempoOpcao = teclado.nextInt();

                if (tempoOpcao == 1 || tempoOpcao == 2) {
                    break;
                }

                System.out.println("Entrada invalida. Digite 1 para Sim ou 2 para Nao.");

            } catch (InputMismatchException ex) {
                System.out.println("Entrada invalida. Digite 1 para Sim ou 2 para Nao.");
                teclado.nextLine();
            }
        }
        if (tempoOpcao == 1) {
            System.out.println("Tempo de execucao: " + getDuracao() + " ms");
        }
        teclado.close();
    }
}
