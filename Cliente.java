/*
    Cliente:
    Solicita o tamanho do vetor ao usuário.
    Solicita a população de forma manual ou automática.
    Envia para o Distribuidor um segmento do vetor para ser ordenado, assim como na parte 1.
*/

import java.util.*;

public class Cliente extends Thread {
    //mover parte de solicitar o tamanho do vetor desejado e a parte de popular o vetor manual ou automaticamente
    Scanner teclado = new Scanner(System.in);
    public static void main(String[] args) {

        Cliente cliente = new Cliente();
        cliente.start();
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
        // Aqui, vetor já é preenchido.

        // Agora, resta enviar ao servidor. (Distribuidor)

        Distribuidor distribuidor = new Distribuidor(vetor, size);
        distribuidor.start();
    }

}
