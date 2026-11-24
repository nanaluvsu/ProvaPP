import java.util.Scanner;

public class Servidor
{
    public static void main(String[] args) throws Exception
    {
        int porta = args.length == 0 ? 3000 : Integer.parseInt(args[0]);

        AceitadoraDeConexao a = new AceitadoraDeConexao(porta);
        a.start();

        System.out.println("Servidor ativo na porta " + porta);
        System.out.println("Digite DESATIVAR para encerrar o servidor.");

        Scanner teclado = new Scanner(System.in);

        while (teclado.hasNextLine())
        {
            if (teclado.nextLine().trim().equalsIgnoreCase("DESATIVAR"))
                break;
        }

        a.desativar();
        a.join();
    }
}