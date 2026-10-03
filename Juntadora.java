import java.util.Vector;

public class Juntadora extends Thread {
    private final Vector<Byte> esquerda;
    private final Vector<Byte> direita;
    private Vector<Byte> resultado;
    private long duracao;

    public Juntadora(Vector<Byte> esquerda, Vector<Byte> direita) {
        this.esquerda = esquerda;
        this.direita = direita;
    }

    public Vector<Byte> getResultado() {
        return resultado;
    }

    public long getDuracao() {
        return duracao;
    }

    private Vector<Byte> merge(Vector<Byte> a, Vector<Byte> b) {

        Vector<Byte> merged = new Vector<>();
        int i = 0, j = 0;

        while (i < a.size() && j < b.size()) {
            if (a.get(i) <= b.get(j)) {
                merged.add(a.get(i));
                i++;
            } else {
                merged.add(b.get(j));
                j++;
            }
        }

        while (i < a.size()) {
            merged.add(a.get(i));
            i++;
        }

        while (j < b.size()) {
            merged.add(b.get(j));
            j++;
        }

        return merged;
    }

    @Override
    public void run() {
        long inicioTempo = System.currentTimeMillis();
        resultado = merge(esquerda, direita);
        long fimTempo = System.currentTimeMillis();
        duracao = fimTempo - inicioTempo;
        this.duracao = duracao;
    }
}