import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.Semaphore;
public class Parceiro {
    private Socket conexao;
    private ObjectOutputStream receptor; //receptor
    private ObjectInputStream transmissor; // transmissor
    private Mensagem proxMensagem = null;

    private Semaphore mutex = new Semaphore(1, true); //pesquisar o que a 2 variavel quer dizer.



    public Socket getConexao() {
        return conexao;
    }
    public void setConexao(Socket conexao) {
        this.conexao = conexao;
    }
    public ObjectOutputStream getReceptor() {
        return receptor;
    }
    public void setReceptor(ObjectOutputStream receptor) {
        this.receptor = receptor;
    }
    public ObjectInputStream getTransmissor() {
        return transmissor;
    }
    public void setTransmissor(ObjectInputStream transmissor) {
        this.transmissor = transmissor;
    }

    public Parceiro(Socket conexao, ObjectOutputStream receptor, ObjectInputStream input) {

        
        setConexao(conexao);
        setReceptor(receptor);
        setTransmissor(input);
    }
    
    public void recebeMensagem(Mensagem msg) throws Exception {
        try {
            receptor = getReceptor();
            receptor.writeObject(msg);
            receptor.flush();
        } catch (IOException ex) {
            System.err.println("Erro na transmissao.");
        }
        
    }

    public Mensagem lookAhead() throws Exception {
        try {
            this.mutex.acquireUninterruptibly();
            if (this.proxMensagem == null) {
                this.proxMensagem = (Mensagem) getTransmissor().readObject();
            }
            this.mutex.release();
            return this.proxMensagem; 
        } catch (Exception ex) {
            throw new Exception("Erro ao ler mensagem.");
        }
        
    }
}
