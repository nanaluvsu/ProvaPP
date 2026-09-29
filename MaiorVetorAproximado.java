import java.util.Scanner;
import java.util.Vector;

public class MaiorVetorAproximado {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        int qtdProcessadores = Runtime.getRuntime().availableProcessors();
        //Vector<Byte> vector = new Vector<>();
        System.err.println("Quantos elementos deseja no vetor?");
        int size = teclado.nextInt();
        Distribuidor distribuidor = new Distribuidor(size);
        //vector.setSize(size);
        System.out.println("Deseja preencher o vetor manualmente ou aleatoriamente com números aleatórios?");
        System.out.println("[1] Manualmente");
        System.out.println("[2] Automaticamente com números aleatórios");
        System.out.println("Aviso: Caso uma opção inválida seja escolhida, o vetor será preenchido automaticamente com números aleatórios.");
        int choice = teclado.nextInt();

        Processadora[] processadores = new Processadora[qtdProcessadores];

        for (int i = 0; i < qtdProcessadores; i++) {
            processadores[i] = new Processadora();
        }

        if (choice == 1) {
            for (int i = 0; i < size; i++) {
                System.out.printf("Digite o elemento %d: ", i + 1);
                byte elemento = teclado.nextByte();
                //vector.set(i, elemento);
            }
        } else {
            for (int i = 0; i < size; i++) {
                byte elemento = (byte) (Math.random() * 256 - 128);
                //vector.set(i, elemento);
                System.out.println("Elemento " + (i + 1) + ": " + elemento);
            }
        }
        
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
    
    }
}
