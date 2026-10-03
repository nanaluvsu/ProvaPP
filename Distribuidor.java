
import java.util.Scanner;
import java.util.Vector;
import java.util.InputMismatchException;

public class Distribuidor extends Thread {

    Vector<Byte> vetor;
    int n;
    int qtdProcessadores = Runtime.getRuntime().availableProcessors();

    private long duracao = 0;
    Processadora[] threadsProcessadoras;
    Scanner teclado;
    private Vector<Long> duracoesThreads = new Vector<>();

    public Distribuidor(Scanner teclado) {
        this.teclado = teclado;
    }

    public long getDuracao() {
        return duracao;
    }

    public Vector<Long> getDuracoesThreads() {
        return duracoesThreads;
    }

    public void setDuracoesThreads(Vector<Long> duracoesThreads) {
        this.duracoesThreads = duracoesThreads;
    }

    @Override
    public void run() {
        long inicioTempo = System.currentTimeMillis();

        try {
            Vector<Byte> vector = new Vector<>();

            System.out.println("Digite o tamanho do vetor:");

            int size = lerInteiro("tamanho do vetor");

            if (size <= 0) {
                System.out.println("O tamanho do vetor deve ser maior que zero.");
                return;
            }

            System.out.println("Deseja preencher o vetor manualmente ou com valores aleatórios?");
            System.out.println("[1] Manualmente [2] Aleatórios");
            System.out.println("Em caso de opção inválida, o vetor será preenchido aleatoriamente.");

            int opcao = lerInteiro("opção de preenchimento");

            if (opcao == 1) {
                for (int i = 0; i < size; i++) {
                    System.out.printf("Digite o valor do elemento %d (-128 a 127): ", i);

                    try {
                        byte valor = lerByte();
                        vector.add(valor);

                    } catch (InputMismatchException ex) {
                        System.out.println(
                            "Valor inválido. Digite um número inteiro entre -128 e 127."
                        );
                        i--;
                    }
                }

            } else {
                if (opcao != 2) {
                    System.out.println("Opção inválida. O vetor será preenchido aleatoriamente.");
                }

                for (int i = 0; i < size; i++) {
                    byte valor = (byte) (Math.random() * 256 - 128);
                    vector.add(valor);
                }
            }

            this.vetor = vector;

            // Evita criar mais threads do que elementos no vetor.
            int qtdThreads = Math.min(
                Math.max(1, qtdProcessadores - 1),
                vetor.size()
            );

            threadsProcessadoras = new Processadora[qtdThreads];

            int base = vetor.size() / qtdThreads;
            int resto = vetor.size() % qtdThreads;
            int inicio = 0;

            for (int i = 0; i < qtdThreads; i++) {
                int fim = inicio + base;

                if (i < resto) {
                    fim++;
                }

                threadsProcessadoras[i] =
                    new Processadora(vetor, inicio, fim);

                threadsProcessadoras[i].setName("Processadora " + (i + 1));
                threadsProcessadoras[i].start();

                inicio = fim;
            }

            // Aguarda todas as threads terminarem.
            for (int i = 0; i < qtdThreads; i++) {
                try {
                    threadsProcessadoras[i].join();
                    duracoesThreads.add(
                        threadsProcessadoras[i].getDuracao()
                    );

                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    System.out.println(
                        "A execução foi interrompida enquanto aguardava as processadoras."
                    );
                    return;
                }
            }

        } catch (InputMismatchException ex) {
            System.out.println(
                "Entrada inválida. Informe números inteiros nos campos solicitados."
            );

        } catch (RuntimeException ex) {
            System.out.println(
                "Erro durante a distribuição do vetor: " + ex.getMessage()
            );

        } finally {
            duracao = System.currentTimeMillis() - inicioTempo;
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

    private byte lerByte() {
        if (!teclado.hasNextInt()) {
            if (teclado.hasNext()) {
                teclado.next();
            }

            throw new InputMismatchException("Valor não inteiro.");
        }

        int valor = teclado.nextInt();

        if (valor < Byte.MIN_VALUE || valor > Byte.MAX_VALUE) {
            throw new InputMismatchException("Valor fora do intervalo de byte.");
        }

        return (byte) valor;
    }
}
