import java.io.*;
import java.net.*;
import java.util.*;
public class Parceiro {
    private Socket conexao;
    private ObjectOutputStream output; //receptor
    private ObjectInputStream input; // transmissor


    public Socket getConexao() {
        return conexao;
    }
    public void setConexao(Socket conexao) {
        this.conexao = conexao;
    }
    public ObjectOutputStream getOutput() {
        return output;
    }
    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }
    public ObjectInputStream getInput() {
        return input;
    }
    public void setInput(ObjectInputStream input) {
        this.input = input;
    }

    public Parceiro(Socket conexao, ObjectOutputStream output, ObjectInputStream input) {

        
        setConexao(conexao);
        setOutput(output);
        setInput(input);
    }
    
}
