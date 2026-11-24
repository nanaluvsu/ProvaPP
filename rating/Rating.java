package rating;
/*
    Client imports lib
    Client calls lib to servers
    Servers receive a n-sized array to be evaluated
    Calculates performance and returns to client
    Result is saved to a csv
    
*/

/*
    Nota: Comentários em inglês servem para futura publicação dessa lib.
    Maligno, desconsidera eles tá td bem ok
*/
import java.util.*;
import java.net.*;
import java.io.*;

public class Rating {
    private int size; //array size
    private int n; // amount of PCs
    private int[] host_ports; // hosts + ports
    Vector<Byte> vetor = new Vector<>();

    public int getSize() {
        return size;
    }
    public void setSize(int size) {
        this.size = size;
    }
    public int getN() {
        return n;
    }
    public void setN(int n) {
        this.n = n;
    }
    private String[] addrs = new String[this.getN()];

    public Rating() {
        for (int i = 0; i < (this.getN() - 1); i++) {
            
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Insira o tamanho do array para o teste: ");
        this.setSize(sc.nextInt());
        for (int i = 0; i < size; i++) {
                byte valor = (byte) (Math.random() * 256 - 128);
                vetor.add(valor);
                System.out.println(valor);
        }

        System.out.println("Valores adicionados com sucesso.");
        sc.close();

    }

}