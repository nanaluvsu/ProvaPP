import java.util.*;

public class Mensagem extends Comunicado {
    private int tipo;
    private Vector<Byte> vetor;    

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Vector<Byte> getVetor() {
        return vetor;
    }

    public void setVetor(Vector<Byte> vetor) {
        this.vetor = vetor;
    }

    public Mensagem(int tipo, Vector<Byte> vetor) {
        setTipo(tipo);
        setVetor(vetor);
    }
}
