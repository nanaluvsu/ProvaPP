import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.InputMismatchException;

public class MaiorVetorAproximado {
    private static final Logger LOGGER = LogUtil.logger(MaiorVetorAproximado.class);

    public static void main(String[] args) {
        Scanner teclado = LogUtil.scanner();
        try {
            LOGGER.info("Aplicação iniciada.");
            System.out.println("Deseja executar o programa com paralelismo ou sem paralelismo?");
            System.out.println("[1] Com paralelismo [2] Sem paralelismo");
            int opcao = lerInteiro(teclado, "modo de execução");

            if (opcao == 1) {
                Distribuidor distribuidor = new Distribuidor();
                distribuidor.start();
                try {
                    distribuidor.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    LOGGER.log(Level.WARNING, "Thread principal interrompida enquanto aguardava o Distribuidor.", e);
                    System.out.println("A execução foi interrompida.");
                    return;
                }
                if (distribuidor.vetor == null) {
                    LOGGER.warning("Distribuidor terminou sem produzir um vetor; operação cancelada.");
                    return;
                }
                System.out.println("Deseja imprimir o vetor ordenado? [1] Sim [2] Nao");
                int printOpcao = lerInteiro(teclado, "opção de impressão do vetor");
                if (printOpcao == 1) {
                    System.out.println("Vetor ordenado:");
                    for (int i = 0; i < distribuidor.vetor.size(); i++) {
                        System.out.print(distribuidor.vetor.get(i) + " ");
                        if ((i + 1) % 25 == 0) System.out.println();
                    }
                    System.out.println();
                } else if (printOpcao != 2) LOGGER.warning("Opção inválida para impressão do vetor: " + printOpcao);

                System.out.println("\nDeseja imprimir o tempo de execução de cada thread (incluindo esta)? [1] Sim [2] Nao");
                int tempoOpcao = lerInteiro(teclado, "opção de impressão dos tempos");
                if (tempoOpcao == 1) {
                    System.out.println("Tempo de execução de cada thread:");
                    for (int i = 0; i < distribuidor.getDuracoesThreads().size(); i++) {
                        System.out.printf("Processadora %d: %d ms%n", i + 1, distribuidor.getDuracoesThreads().get(i));
                    }
                    System.out.println("Tempo de execução de Distribuidor: " + distribuidor.getDuracao() + " ms");
                } else if (tempoOpcao != 2) LOGGER.warning("Opção inválida para impressão dos tempos: " + tempoOpcao);
            } else if (opcao == 2) {
                new ProgramaSemParalelismo().mergeExecute();
            } else {
                LOGGER.warning("Modo de execução inválido: " + opcao);
                System.out.println("Opção inválida. Encerrando o programa.");
            }
        } catch (InputMismatchException e) {
            LOGGER.log(Level.WARNING, "Entrada inválida no menu principal.", e);
            System.out.println("Entrada inválida. Informe uma opção numérica.");
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Erro inesperado na aplicação.", e);
            System.out.println("Ocorreu um erro inesperado. Consulte logs/aplicacao.log.");
        } finally {
            LOGGER.info("Aplicação finalizada.");
        }
    }

    private static int lerInteiro(Scanner teclado, String campo) {
        if (!teclado.hasNextInt()) {
            String recebido = teclado.hasNext() ? teclado.next() : "fim da entrada";
            LOGGER.warning("Entrada não inteira para " + campo + ": " + recebido);
            throw new InputMismatchException("Era esperado um número inteiro para " + campo + ".");
        }
        return teclado.nextInt();
    }
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
        System.out.println("\nMaior vetor que coube (aproximadamente): " + String.format("%,d", ultimoBemSucedido));
        System.out.printf("Memória estimada: %.2f MB%n", ultimoBemSucedido * 1.0 / (1024 * 1024));
        System.out.printf("Tempo total: %.2f segundos%n", (fim - inicio)/1000.0);
        */
    }
}
