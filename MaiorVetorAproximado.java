import java.util.Scanner;
import java.util.InputMismatchException;

//maligno malefico se vc ta lendo isso o desenvolvimento comeca dps ok mt barulho na sala 
public class MaiorVetorAproximado {
    public static void main(String[] args) {
        int opcao = 0;
        int printOpcao = 0;
        int tempoOpcao = 0;
        Scanner teclado = new Scanner(System.in);
        opcao = 0;
        while (true) {
            try {
                System.out.println("Deseja executar o programa com paralelismo ou sem paralelismo?");
                System.out.println("[1] Com paralelismo [2] Sem paralelismo");
                opcao = teclado.nextInt();
                if (opcao == 1 || opcao == 2) {
                    break;
                }
            } catch (InputMismatchException ex) {
                System.out.println("Opcao invalida. Digite 1 ou 2.");
                teclado.nextLine();
            }
        }
        if (opcao == 1) {
            Distribuidor distribuidor = new Distribuidor(teclado);
            distribuidor.start();

            try {
                distribuidor.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
    for (int i = 0; i < distribuidor.getVetor().size(); i++) {
        System.out.print(distribuidor.getVetor().get(i) + " ");
    }
} else {
    int inicio, fim;

    while (true) {
        try {
            System.out.println("Digite o indice inicial: ");
            inicio = teclado.nextInt();

            System.out.println("Digite o indice final: ");
            fim = teclado.nextInt();

            if (inicio >= 0 && fim >= inicio && fim < distribuidor.getVetor().size()) {
                break;
            }

            System.out.println("Intervalo invalido.");
        } catch (InputMismatchException ex) {
            System.out.println("Digite valores inteiros validos.");
            teclado.nextLine();
        }
    }

    for (int i = inicio; i <= fim; i++) {
        System.out.print(distribuidor.getVetor().get(i) + " ");
    }
}
            while (true) {
                try {
                    System.out.println(
                            "\nDeseja imprimir o tempo de execucao de cada thread (incluindo esta)? [1] Sim [2] Nao");
                    tempoOpcao = teclado.nextInt();

                    if (tempoOpcao == 1 || tempoOpcao == 2) {
                        break;
                    }

                    System.out.println("Opcao invalida. Digite 1 para Sim ou 2 para Nao.");
                } catch (InputMismatchException ex) {
                    System.out.println("Entrada invalida. Digite 1 para Sim ou 2 para Nao.");
                    teclado.nextLine();
                }
            }
            if (tempoOpcao == 1) {
                System.out.println("Tempo de execucao de cada thread:");
                for (int i = 0; i < distribuidor.getDuracoesThreadsProcessadoras().size(); i++) {
                    System.out.printf("Processadora %d: %dms%n", i + 1,
                            distribuidor.getDuracoesThreadsProcessadoras().get(i));
                }
                for (int i = 0; i < distribuidor.getDuracoesThreadsJuntadoras().size(); i++) {
                    System.out.printf("Juntadora %d: %dms%n", i + 1,
                            distribuidor.getDuracoesThreadsJuntadoras().get(i));
                }
                System.out.println("\nTempo de execucao de Distribuidor: " + distribuidor.getDuracao() + "ms");
            }
        } else if (opcao == 2) {
            ProgramaSemParalelismo programa = new ProgramaSemParalelismo(teclado);
            programa.mergeExecute();
        } else {
            System.out.println("Opcao invalida. Encerrando o programa.");
        }

        /*
         * System.out.println("Estimando o maior tamanho possível de vetor em Java...");
         * long inicio = System.currentTimeMillis();
         * 
         * int tamanho = 1_000_000;
         * int ultimoBemSucedido = 0;
         * 
         * while (true) {
         * try {
         * byte[] vetor = new byte[tamanho];
         * ultimoBemSucedido = tamanho;
         * vetor = null;
         * System.gc();
         * 
         * if (tamanho > Integer.MAX_VALUE / 3 * 2) break;
         * 
         * tamanho /= 2;
         * tamanho *= 3;
         * 
         * System.out.printf("Alocado com sucesso:%,d elementos%n", ultimoBemSucedido);
         * } catch (OutOfMemoryError e) {
         * System.out.printf("Falhou em %,d elementos%n", tamanho);
         * break;
         * }
         * }
         * 
         * long fim = System.currentTimeMillis();
         * System.out.println("\nMaior vetor que coube (aproximadamente): " +
         * String.format("%,d", ultimoBemSucedido));
         * System.out.printf("Memória estimada: %.2f MB%n", ultimoBemSucedido * 1.0 /
         * (1024 * 1024));
         * System.out.printf("Tempo total: %.2f segundos%n", (fim - inicio)/1000.0);
         */
    }
}
