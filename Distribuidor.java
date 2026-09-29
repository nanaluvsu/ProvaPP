import java.util.Vector;

public class Distribuidor extends Thread {
    Vector<Byte> vetor;
    int n;
    int qtdProcessadores = Runtime.getRuntime().availableProcessors();

    Processadora[] threadsProcessadoras = new Processadora[qtdProcessadores - 1];

    for (int i = 0; i < qtdProcessadores - 1; i++) {
        threadsProcessadoras[i] = new Processadora(vetor, i * (n / qtdProcessadores), (i + 1) * (n / qtdProcessadores));
        threadsProcessadoras[i].start();
    }


    public Distribuidor(int n) {
        this.n = n;
    }

    
    public void run() {
        vetor = new Vector<Byte>(n);
        vetor.setSize(n);
    }

}
