/* AceitadoraDeConexao 
Inicia thread SupervisoraDeConexao e esperar encerramento usando join().
*/
import java.net.*;
import java.util.*;
import java.io.*;
import java.lang.reflect.Array;
public class AceitadoraDeConexao extends Thread {
    private ServerSocket req;
    private ArrayList<Parceiro> usuarios;
    private int porta;

    public AceitadoraDeConexao(int porta) {
        this.porta = porta;
    }
}
