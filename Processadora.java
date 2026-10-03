
import java.util.Vector;

public class Processadora extends Thread {

    // Vetor e intervalo que esta thread deve ordenar.
    private Vector<Byte> vetor;
    private int inicio;
    private int fim;
    private long duracao;

    public Processadora(Vector<Byte> vetor, int inicio, int fim) {
        if (vetor == null) {
            throw new IllegalArgumentException("O vetor não pode ser nulo.");
        }

        if (inicio < 0 || fim < inicio || fim > vetor.size()) {
            throw new IllegalArgumentException("Intervalo inválido para ordenação.");
        }

        this.vetor = vetor;
        this.inicio = inicio;
        this.fim = fim;
    }

    public long getDuracao() {
        return duracao;
    }

    private void mergeSort(Vector<Byte> vetor, int inicio, int fim) {
        if (inicio >= fim) {
            return;
        }

        int meio = inicio + (fim - inicio) / 2;

        mergeSort(vetor, inicio, meio);
        mergeSort(vetor, meio + 1, fim);

        merge(vetor, inicio, meio, fim);
    }

    private void merge(Vector<Byte> vetor, int inicio, int meio, int fim) {
        if (inicio >= fim) {
            return;
        }

        byte[] esq = new byte[meio - inicio + 1];
        byte[] dir = new byte[fim - meio];

        for (int i = 0; i < esq.length; i++) {
            esq[i] = vetor.get(inicio + i);
        }

        for (int i = 0; i < dir.length; i++) {
            dir[i] = vetor.get(meio + 1 + i);
        }

        int i = 0;
        int j = 0;
        int k = inicio;

        while (i < esq.length && j < dir.length) {
            if (esq[i] <= dir[j]) {
                vetor.set(k, esq[i]);
                i++;
            } else {
                vetor.set(k, dir[j]);
                j++;
            }

            k++;
        }

        while (i < esq.length) {
            vetor.set(k, esq[i]);
            i++;
            k++;
        }

        while (j < dir.length) {
            vetor.set(k, dir[j]);
            j++;
            k++;
        }
    }

    @Override
    public void run() {
        long inicioTempo = System.currentTimeMillis();

        try {
            mergeSort(vetor, inicio, fim - 1);

            System.out.println(
                getName() + " ordenou [" + inicio + ", " + (fim - 1) + "]"
            );

        } catch (IndexOutOfBoundsException e) {
            System.out.println(
                "Erro: índice inválido durante a ordenação em " + getName() + "."
            );

        } catch (RuntimeException e) {
            System.out.println(
                "Erro ao ordenar o trecho do vetor: " + e.getMessage()
            );

        } finally {
            duracao = System.currentTimeMillis() - inicioTempo;

            System.out.println(
                getName() + " duração: " + duracao + " ms"
            );
        }
    }
}
