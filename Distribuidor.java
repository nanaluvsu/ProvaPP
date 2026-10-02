import java.util.Scanner;
import java.util.Vector;

public class Distribuidor extends Thread {
    Vector<Byte> vetor;
    int n;
    int qtdProcessadores = Runtime.getRuntime().availableProcessors();
    private long duracao = 0;
    Processadora[] threadsProcessadoras;
    Scanner teclado;
    private Vector<Long> duracoesThreads = new Vector<>();

    public long getDuracao() {
        return duracao;
    }

    public Vector<Long> getDuracoesThreads() {
        return duracoesThreads;
    }

    public void setDuracoesThreads(Vector<Long> duracoesThreads) {
        this.duracoesThreads = duracoesThreads;
    }

    public Distribuidor(Scanner teclado) {
        this.teclado = teclado;
    }


    public void run() {
        long inicioTempo = System.currentTimeMillis();
        Vector<Byte> vector = new Vector<>();
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
                vector.add(valor);
            }
        } else {
            for (int i = 0; i < size; i++) {
                byte valor = (byte) (Math.random() * 100);
                vector.add(valor);
            }
        }
        this.vetor = vector;

        int qtdThreads = qtdProcessadores - 1; 
        int base = vetor.size() / qtdThreads; // Tamanho base de cada thread
        int resto = vetor.size() % qtdThreads; //Como o vetor pode não ser divisível, é importante considerar o resto na equação.
        Processadora[] threads = new Processadora[qtdProcessadores - 1];
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

            inicio = fim; //proxima thread começa do fim da anterior
        }
        // ao final, o Distribuidor utiliza join() para aguardar a conclusão de todas as threads Processadoras antes de prosseguir. Isso garante que o vetor seja totalmente ordenado antes de qualquer operação subsequente.
        for (int i = 0; i < qtdThreads; i++) {
            try {
                threads[i].join();
                duracoesThreads.add(threads[i].getDuracao());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        

        long fimTempo = System.currentTimeMillis();
        duracao = fimTempo - inicioTempo;

    }
    
}
