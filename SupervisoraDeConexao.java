/* SupervisoraDeConexao
    Atende às mensagens solicitando ordenação ou merge.
*/

/*
    "Classe que deverá conter um vetor": Supervisora será ligada ao socket. Dentro do socket, ocorre a ordenação do vetor. 
*/
import java.util.*;
import java.net.*;
public class SupervisoraDeConexao {
    private Parceiro parceiro;
    private Socket conexao;
    private ArrayList<Parceiro> usuarios;

    public SupervisoraDeConexao(Parceiro parceiro, Socket conexao, ArrayList<Parceiro> usuarios) {
        this.parceiro = parceiro;
        this.conexao = conexao;
        this.usuarios = usuarios;
    }
    
}