import java.util.Scanner;
import java.util.Vector;

public class Distribuidor extends Thread {
    Vector<Byte> vetor;
    int n;
    int qtdProcessadores = Runtime.getRuntime().availableProcessors();

    Processadora[] threadsProcessadoras;

    public Distribuidor() {
        threadsProcessadoras = new Processadora[qtdProcessadores];
    }

    public void run() {
        Scanner teclado = new Scanner(System.in);
        Vector<Byte> vector = new Vector<>();
        System.out.println("Digite o tamanho do vetor: ");
        int size = teclado.nextInt();
        System.out.println("Deseja preencher o vetor manualmente ou com valores aleatórios?");
        System.out.println("[1] Manualmente [2] Aleatórios");
        System.out.println("Em caso de opção inválida, o vetor será preenchido com valores aleatórios.");
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
        teclado.close();
        this.vetor = vector;
    }
    
}
