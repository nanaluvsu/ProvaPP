import java.util.Scanner;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.InputMismatchException;

public class Distribuidor extends Thread {
    private static final Logger LOGGER = LogUtil.logger(Distribuidor.class);
    Vector<Byte> vetor;
    int n;
    int qtdProcessadores = Runtime.getRuntime().availableProcessors();
    private long duracao = 0;
    Processadora[] threadsProcessadoras;
    private Vector<Long> duracoesThreads = new Vector<>();

    public long getDuracao() { return duracao; }
    public Vector<Long> getDuracoesThreads() { return duracoesThreads; }
    public void setDuracoesThreads(Vector<Long> duracoesThreads) {
        this.duracoesThreads = duracoesThreads;
    }

    @Override
    public void run() {
        long inicioTempo = System.currentTimeMillis();
        Scanner teclado = LogUtil.scanner();
        Vector<Byte> vector = new Vector<>();
        try {
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
                    vector.add((byte) entrada);
                }
            } else {
                if (opcao != 2) LOGGER.warning("Opção de preenchimento inválida (" + opcao + "); usando valores aleatórios.");
                for (int i = 0; i < size; i++) vector.add((byte) (Math.random() * 100));
            }
            this.vetor = vector;
            LOGGER.info("Vetor criado com " + size + " elementos.");

            int qtdThreads = Math.max(1, qtdProcessadores - 1);
            qtdThreads = Math.min(qtdThreads, vetor.size());
            int base = vetor.size() / qtdThreads;
            int resto = vetor.size() % qtdThreads;
            Processadora[] threads = new Processadora[qtdThreads];
            this.threadsProcessadoras = threads;
            int inicio = 0;

            for (int i = 0; i < qtdThreads; i++) {
                int fim = inicio + base + (i < resto ? 1 : 0);
                if (fim > vetor.size()) {
                    LOGGER.warning("Índice final excedeu o vetor; ajustando para " + vetor.size() + ".");
                    fim = vetor.size();
                }
                threads[i] = new Processadora(vetor, inicio, fim);
                threads[i].setName("Processadora " + (i + 1));
                LOGGER.info("Iniciando " + threads[i].getName() + " para índices [" + inicio + ", " + (fim - 1) + "].");
                threads[i].start();
                inicio = fim;
            }

            for (Processadora thread : threads) {
                try {
                    thread.join();
                    duracoesThreads.add(thread.getDuracao());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    LOGGER.log(Level.WARNING, "Distribuidor interrompido ao aguardar " + thread.getName() + ".", e);
                    return;
                }
            }
            LOGGER.info("Todas as processadoras concluíram a ordenação.");
        } catch (InputMismatchException e) {
            LOGGER.log(Level.WARNING, "Entrada inválida durante a distribuição do vetor.", e);
            System.out.println("Entrada inválida. Informe números inteiros válidos.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            LOGGER.log(Level.SEVERE, "Erro ao preparar ou distribuir o vetor.", e);
            System.out.println("Não foi possível distribuir o vetor: " + e.getMessage());
        } catch (RuntimeException e) {
            LOGGER.log(Level.SEVERE, "Erro inesperado no Distribuidor.", e);
            System.out.println("Ocorreu um erro inesperado durante a execução.");
        } finally {
            duracao = System.currentTimeMillis() - inicioTempo;
            LOGGER.info("Execução do Distribuidor finalizada em " + duracao + " ms.");
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
