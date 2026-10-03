import java.util.Scanner;
import java.util.Vector;
import java.util.InputMismatchException; //InputMismatchException é a mais adequada para valores inadequados

public class Distribuidor extends Thread {
    private Vector<Byte> vetor;
    int n;
    int qtdProcessadores = Runtime.getRuntime().availableProcessors();
    private long duracao;
    Processadora[] threadsProcessadoras;
    Scanner teclado;
    private Vector<Long> duracoesThreadsProcessadoras = new Vector<>();
    private Vector<Long> duracoesThreadsJuntadoras = new Vector<>();

    public long getDuracao() {
        return duracao;
    }

    public Vector<Long> getDuracoesThreadsProcessadoras() {
        return duracoesThreadsProcessadoras;
    }

    public Vector<Long> getDuracoesThreadsJuntadoras() {
        return duracoesThreadsJuntadoras;
    }

    public Distribuidor(Scanner teclado) {
        this.teclado = teclado;
    }

    public Vector<Byte> getVetor() {
        return vetor;
    }


    private Vector<Byte> juntarVetoresOrdenados(Vector<Vector<Byte>> vetorJoin) {
        int rodadaExecucao = 1;
        while (vetorJoin.size() > 1) {
            System.out.println("\n\n\n Rodada de execucao: " + rodadaExecucao);
            System.out.println("Quantidade de vetores antes da rodada: " + vetorJoin.size());
            Vector<Juntadora> juntadoras = new Vector<>(); // vetor de threads juntadoras
            Vector<Vector<Byte>> proximaRodada = new Vector<>(); // vetor que armazenará os vetores resultantes da próxima rodada de junção

            for (int i = 0; i + 1 < vetorJoin.size(); i += 2) { //enquanto houver pares de vetores, cria uma thread juntadora para cada par
                System.out.println("Juntadora " + (i / 2 + 1) + " unindo partes " + i + " e " + (i + 1));
                Juntadora juntadora = new Juntadora(vetorJoin.get(i), vetorJoin.get(i + 1));  //cria uma thread juntadora recebendo  
                juntadora.setName("Juntadora " + (i / 2 + 1)); //nomeia a thread juntadora
                juntadora.start();
                juntadoras.add(juntadora); //adiciona a thread ao vetor de threads
            }

            for (int i = 0; i < juntadoras.size(); i++) { // enquanto houver threads juntadoras, aguarda a conclusão de cada uma e adiciona o resultado ao vetor da próxima rodada
            try {
                juntadoras.get(i).join();
                duracoesThreadsJuntadoras.add(juntadoras.get(i).getDuracao());
                proximaRodada.add(juntadoras.get(i).getResultado()); //adiciona o resultado da thread juntadora ao vetor da próxima rodada
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

            if (vetorJoin.size() % 2 != 0) {
                proximaRodada.add(vetorJoin.get(vetorJoin.size() - 1));
            }

            vetorJoin = proximaRodada;
            System.out.println("Rodada " + rodadaExecucao + " concluida. Vetores restantes: " + vetorJoin.size());
            rodadaExecucao++;
        }

        System.out.println("Vetor final ordenado concluido.");
        return vetorJoin.get(0);

    }

    public void run() {
        int size = 0;
        long inicioTempo = System.currentTimeMillis();
        Vector<Byte> vetor = new Vector<>();
        Vector<Vector<Byte>> vetorJoin = new Vector<>();

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
        int opcao = teclado.nextInt();
        if (opcao == 1) {
            for (int i = 0; i < size; i++) {
                try {
                    System.out.printf("Digite o valor do elemento %d: ", i);
                    byte valor = teclado.nextByte();
                    vetor.add(valor);
                } catch (InputMismatchException ex) {
                    System.out.println("Valor excede range de byte.");
                    teclado.nextLine();
                    i--;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                byte valor = (byte) (Math.random() * 256 - 128);
                vetor.add(valor);
            }
        }
        this.vetor = vetor;

        int qtdThreads = qtdProcessadores - 1;
        if (qtdThreads <= 0) {
            qtdThreads = 1;
        }

        System.out.println("Vetor dividido em " + qtdThreads + " partes.");

        int base = vetor.size() / qtdThreads;
        int resto = vetor.size() % qtdThreads;
        Processadora[] threads = new Processadora[qtdThreads];
        int inicio = 0;

        for (int i = 0; i < qtdThreads; i++) {
            int fim = inicio + base;

            if (i < resto) {
                fim++;
            }

            if (fim > vetor.size()) {
                System.out.println("indice final ultrapassa o tamanho do vetor. Ajustando para o tamanho maximo.");
                fim = vetor.size();
            }

            threads[i] = new Processadora(vetor, inicio, fim);
            threads[i].setName("Processadora " + (i + 1));
            threads[i].start();

            inicio = fim;
        }

        for (int i = 0; i < qtdThreads; i++) {
            try {
                threads[i].join();
                duracoesThreadsProcessadoras.add(threads[i].getDuracao());
                vetorJoin.add(threads[i].getParteVetor());
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        Vector<Byte> vetorOrdenado = juntarVetoresOrdenados(vetorJoin);
        this.vetor = vetorOrdenado;
        long fimTempo = System.currentTimeMillis();
        duracao = fimTempo - inicioTempo;
        this.duracao = duracao;
    }
}
