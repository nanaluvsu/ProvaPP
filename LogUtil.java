
import java.util.Scanner;

/**
 * Configuração compartilhada de entrada da aplicação.
 */
public final class LogUtil {

    private static final Scanner SCANNER = new Scanner(System.in);

    private LogUtil() {
    }

    public static Scanner scanner() {
        return SCANNER;
    }

    public static void erro(String mensagem) {
        System.out.println("Erro: " + mensagem);
    }

    public static void aviso(String mensagem) {
        System.out.println("Aviso: " + mensagem);
    }

    public static void informacao(String mensagem) {
        System.out.println(mensagem);
    }
}
