import java.io.*;
import java.util.Vector;

public class Comunicado implements Serializable, Cloneable
{
    private int tipo;
    /* Possíveis tipos:
        1. Ordenação
        2. Merge
        3. Resultado de ordenação
        4. Solicitação  de merge.
        5. Resposta
    */
    private Vector<Byte> vetor; //Vetor resultante
}
