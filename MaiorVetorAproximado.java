
import java.util.InputMismatchException;
import java.util.Scanner;

// maligno malefico se vc ta lendo isso o desenvolvimento comeca dps ok mt barulho na sala

public class MaiorVetorAproximado {

    public static void main(String[] args) {
        Scanner teclado = LogUtil.scanner();

        try {
            System.out.println("Deseja executar o programa com paralelismo ou sem paralelismo?");
            System.out.println("[1] Com paralelismo [2] Sem paralelismo");

            int opcao = lerInteiro(teclado, "modo de execução");

            if (opcao == 1) {
                Distribuidor distribuidor = new Distribuidor(teclado);
                distribuidor.start();

                try {
                    distribuidor.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("A execução foi interrompida.");
                    return;
                }

                if (distribuidor.vetor == null) {
                    System.out.println("Não foi possível gerar o vetor.");
                    return;
                }

                System.out.println("Deseja imprimir o vetor ordenado? [1] Sim [2] Não");
                int printOpcao = lerInteiro(teclado, "opção de impressão");

                if (printOpcao == 1) {
                    System.out.println("Vetor ordenado:");

                    for (int i = 0; i < distribuidor.vetor.size(); i++) {
                        System.out.print(distribuidor.vetor.get(i) + " ");

                        if ((i + 1) % 25 == 0) {
                            System.out.println();
                        }
                    }

                    System.out.println();

                } else if (printOpcao != 2) {
                    System.out.println("Opção de impressão inválida.");
                }

                System.out.println(
                    "\nDeseja imprimir o tempo de execução de cada thread (incluindo esta)? [1] Sim [2] Não"
                );

                int tempoOpcao = lerInteiro(teclado, "opção de impressão dos tempos");

                if (tempoOpcao == 1) {
                    System.out.println("Tempo de execução de cada thread:");

                    for (int i = 0;
                         i < distribuidor.getDuracoesThreads().size();
                         i++) {

                        System.out.printf(
                            "Processadora %d: %d ms%n",
                            i + 1,
                            distribuidor.getDuracoesThreads().get(i)
                        );
                    }

                    System.out.println(
                        "Tempo de execução de Distribuidor: "
                        + distribuidor.getDuracao() + " ms"
                    );

                } else if (tempoOpcao != 2) {
                    System.out.println("Opção para imprimir os tempos inválida.");
                }

            } else if (opcao == 2) {
                ProgramaSemParalelismo programa =
                    new ProgramaSemParalelismo(teclado);

                programa.mergeExecute();

            } else {
                System.out.println("Opção inválida. Encerrando o programa.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Informe um número inteiro.");

        } catch (RuntimeException e) {
            System.out.println(
                "Ocorreu um erro durante a execução: " + e.getMessage()
            );
        }
    }

    private static int lerInteiro(Scanner teclado, String campo) {
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

    /*
    System.out.println("Estimando o maior tamanho possível de vetor em Java...");
    long inicio = System.currentTimeMillis();

    int tamanho = 1_000_000;
    int ultimoBemSucedido = 0;

    while (true) {
        try {
            byte[] vetor = new byte[tamanho];
            ultimoBemSucedido = tamanho;
            vetor = null;
            System.gc();

            if (tamanho > Integer.MAX_VALUE / 3 * 2) break;

            tamanho /= 2;
            tamanho *= 3;

            System.out.printf("Alocado com sucesso:%,d elementos%n", ultimoBemSucedido);
        } catch (OutOfMemoryError e) {
            System.out.printf("Falhou em %,d elementos%n", tamanho);
            break;
        }
    }

    long fim = System.currentTimeMillis();
    System.out.println("\nMaior vetor que coube (aproximadamente): "
        + String.format("%,d", ultimoBemSucedido));
    System.out.printf("Memória estimada: %.2f MB%n",
        ultimoBemSucedido * 1.0 / (1024 * 1024));
    System.out.printf("Tempo total: %.2f segundos%n",
        (fim - inicio) / 1000.0);
    */
}
